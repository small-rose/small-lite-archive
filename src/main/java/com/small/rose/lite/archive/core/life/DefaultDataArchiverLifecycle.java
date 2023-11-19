package com.small.rose.lite.archive.core.life;

import com.small.rose.lite.archive.core.JobScheduler;
import com.small.rose.lite.archive.core.job.ArchiveTask;
import com.small.rose.lite.archive.core.job.Callback;
import com.small.rose.lite.archive.core.job.Job;
import com.small.rose.lite.archive.core.job.Task;
import com.small.rose.lite.archive.emuns.ArchiveJobStatusEnum;
import com.small.rose.lite.archive.exception.DataArchiverException;
import com.small.rose.lite.archive.module.buz.ArchiveJob2TaskService;
import com.small.rose.lite.archive.module.buz.ArchiveJobConfigCheckService;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTask;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTaskDetail;
import com.small.rose.lite.archive.module.service.jdbc.ArchiveSqlService;
import com.small.rose.lite.archive.module.service.jpa.AmsArchiveTaskDetailService;
import com.small.rose.lite.archive.utils.SmallUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ DefaultDataArchiverLifecycle ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 12:44
 * @Version: v1.0
 */
@Slf4j
@Component
public class DefaultDataArchiverLifecycle extends AbstractDataArchiverLifecycle{


    @Autowired
    private ArchiveJobConfigCheckService archiveJobConfigCheckService;
    @Autowired
    private ArchiveJob2TaskService archiveJob2TaskService;
    @Autowired
    private AmsArchiveTaskDetailService amsArchiveTaskDetailService;
    @Autowired
    private ArchiveSqlService archiveSqlService;
    @Autowired
    private JobScheduler jobScheduler;


    /**
     * 校验配置、验证是否可以执行归档
     * @param jobConfig
     * @return
     * @throws DataArchiverException
     */
    @Override
    protected boolean checkConfig(AmsArchiveJobConfig jobConfig) throws DataArchiverException {

        return archiveJobConfigCheckService.check(jobConfig);
    }

    @Override
    protected boolean initialize(AmsArchiveJobConfig jobConfig) throws DataArchiverException {

        return false;
    }

    /**
     * 生成对应归档策略的 批次和任务明细
     * @param jobConfig 源数据对象
     * @return
     * @throws DataArchiverException
     */
    @Override
    protected AmsArchiveTask generateBatchTask(AmsArchiveJobConfig jobConfig) throws DataArchiverException {
        return archiveJob2TaskService.convert(jobConfig);
    }

    /**
     *  获取任务明细 -分片任务
     * @param archiveTask
     * @return
     * @throws DataArchiverException
     */
    @Override
    protected List<AmsArchiveTaskDetail> dataSharding(AmsArchiveTask archiveTask) throws DataArchiverException {
        List<AmsArchiveTaskDetail> detailList = amsArchiveTaskDetailService.findPrepareTaskDetailList(archiveTask);
        return detailList;
    }

    /**
     * 将 AmsArchiveTaskDetail 转成可执行的 ArchiveTask，并注册回调函数，执行完毕立即执行校验
     * @param archiveTask 目标数据对象
     * @return
     * @throws DataArchiverException
     */
    @Override
    protected ArchiveTask processTask(AmsArchiveTaskDetail archiveTask) throws DataArchiverException {
        return new ArchiveTask(archiveTask, new Callback() {
            @Override
            public void onTaskSuccess(Task task) {
                ArchiveTask archiveTask = (ArchiveTask) task;
                verifyTask(archiveTask.getDetailTask());
            }

            @Override
            public void onTaskFailure(Task task, Throwable t) {
                ArchiveTask archiveTask = (ArchiveTask) task;
                AmsArchiveTaskDetail detailTask = archiveTask.getDetailTask();
                String ex = ExceptionUtils.getStackTrace(t);
                detailTask.setTaskStatus(ArchiveJobStatusEnum.ERROR_FAILED.getStatus());
                detailTask.setErrorInfo(ex);
                amsArchiveTaskDetailService.saveAndFlush(detailTask);
            }
        });
    }


    @Override
    protected void schedule(Job job) throws DataArchiverException {
        jobScheduler.scheduleJob(job);
    }


    @Override
    protected boolean proofreading(AmsArchiveTask archiveTask) throws DataArchiverException {
        boolean proofResult = false ;
        int reTry = 0 ;
        // 一个任务会阻塞等待 1分钟
        while (!proofResult && reTry < 30){
            List<AmsArchiveTaskDetail> detailList = amsArchiveTaskDetailService.findPrepareTaskDetailList(archiveTask);

            boolean match = detailList.stream().allMatch(d -> ArchiveJobStatusEnum.VERIFY_SUCCESS.name().equalsIgnoreCase(d.getTaskStatus()));
            if (match){
                // 全部是 VERIFY_SUCCESS 状态则退出循环
                proofResult = true ;
            }
            // 校验已经搬运完成的数据
            /*
            List<AmsArchiveTaskDetail> slList = amsArchiveTaskDetailService.findTaskDetailList(archiveTask, ArchiveJobStatusEnum.MIGRATED_SUCCESS);
            for (AmsArchiveTaskDetail taskDetail : slList) {
                verifyTask(taskDetail);
            }*/
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reTry++;
        }
        return proofResult;
    }

    /**
     * 先执行完的可以先校验
     * @param taskDetail
     * @return
     * @throws DataArchiverException
     */
    @Override
    protected AmsArchiveTaskDetail verifyTask(AmsArchiveTaskDetail taskDetail) throws DataArchiverException {

        taskDetail.setVerifyStart(new Date());
        Long verifySize = archiveSqlService.queryVerifySize(taskDetail);
        taskDetail.setVerifySize(verifySize);
        if (SmallUtils.isNotEmpty(taskDetail.getActualSize()) && verifySize==taskDetail.getActualSize()){
            taskDetail.setTaskStatus(ArchiveJobStatusEnum.VERIFY_SUCCESS.getStatus());
         }else{
            taskDetail.setTaskStatus(ArchiveJobStatusEnum.VERIFY_FAILED.getStatus());
        }
        taskDetail.setVerifyEnd(new Date());
        return amsArchiveTaskDetailService.saveAndFlush(taskDetail);
    }


    @Override
    protected void clear(AmsArchiveTask archiveTask) throws DataArchiverException {
        List<AmsArchiveTaskDetail> detailList = amsArchiveTaskDetailService.findPrepareTaskDetailList(archiveTask);
        for (AmsArchiveTaskDetail taskDetail : detailList) {
            clearTaskDetail(taskDetail);
        }
    }

    @Override
    protected void clearTaskDetail(AmsArchiveTaskDetail archiveTask) throws DataArchiverException {
        archiveTask.setDeleteStart(new Date());
        long delCount = archiveSqlService.deleteArchivedData(archiveTask);
        archiveTask.setDeleteSize(delCount);
        archiveTask.setDeleteEnd(new Date());
        amsArchiveTaskDetailService.saveAndFlush(archiveTask);
    }

    @Override
    protected void finalize() throws DataArchiverException {

    }


}
