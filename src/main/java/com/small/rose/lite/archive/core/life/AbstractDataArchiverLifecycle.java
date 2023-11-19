package com.small.rose.lite.archive.core.life;

import com.small.rose.lite.archive.core.job.ArchiveJob;
import com.small.rose.lite.archive.core.job.ArchiveTask;
import com.small.rose.lite.archive.core.job.Job;
import com.small.rose.lite.archive.exception.DataArchiverException;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTask;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTaskDetail;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ AbstractDataArchiverLifecycle ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 12:40
 * @Version: v1.0
 */
@Slf4j
public abstract class AbstractDataArchiverLifecycle extends DataArchiverContext implements DataArchiverLifecycle{


    /**
     * 归档主逻辑
     * @param jobConfig
     * @throws DataArchiverException
     */
    @Override
    public void execute(AmsArchiveJobConfig jobConfig) throws DataArchiverException {
        // 预留初始化操作
        initialize(jobConfig);
        // 检查配置、检查是否执行归档
        boolean result = checkConfig(jobConfig);
        if (!result){
            log.info(">>> 配置与校验检查未通过，不执行归档任务");
            return;
        }
         // 生成本批次的归档任务
        AmsArchiveTask amsArchiveTask = generateBatchTask(jobConfig);

        // 提取本次的归档任务明细 （是否分页？）
        List<AmsArchiveTaskDetail> archiveTaskList = dataSharding(amsArchiveTask);
        // 转成成调度任务
        List<ArchiveTask> taskList = archiveTaskList.stream().map(a -> processTask(a)).collect(Collectors.toList());
        Job job = new ArchiveJob(amsArchiveTask);
        job.getArchiveTaskList().addAll(taskList);
        // 执行调度批次归档任务
        schedule(job);

        // 当前批次数据校对
        if (proofreading(amsArchiveTask)){
            // 清理本批次全部归档好的数据
            clear(archiveTask);
        }else{
            log.info(">>> 本批次任务出现执行失败 >>> 无法执行数据清理 ");
            throw new DataArchiverException("本批次任务出现执行失败,需人工确认检查");
        }
        // 预留收尾操作
        finalize();
    }
    /**
     * 初始化  - 检查配置
     *        - 转换成批次
     * @throws DataArchiverException 数据归档异常
     */
    protected abstract boolean checkConfig(AmsArchiveJobConfig jobConfig) throws DataArchiverException;
    /**
     * 初始化  - 检查配置
     *        - 转换成批次
     * @throws DataArchiverException 数据归档异常
     */
    protected abstract boolean initialize(AmsArchiveJobConfig jobConfig) throws DataArchiverException;


    /**
     *
     * @param jobConfig 源数据对象
     * @return 目标数据对象
     * @throws DataArchiverException 数据归档异常
     */
    protected abstract AmsArchiveTask generateBatchTask(AmsArchiveJobConfig jobConfig) throws DataArchiverException;

    /**
     * 获取任务数据明细-分片任务
     * @return 源数据对象
     * @throws DataArchiverException 数据归档异常
     */
    protected abstract List<AmsArchiveTaskDetail>  dataSharding(AmsArchiveTask archiveTask) throws DataArchiverException;

    /**
     * 处理目标数据
     * @param archiveTask 目标数据对象
     * @throws DataArchiverException 数据归档异常
     */
    protected abstract ArchiveTask processTask(AmsArchiveTaskDetail archiveTask) throws DataArchiverException;

    /**
     * schedule 归档任务
     * @param job  抽象成可执行 Job
     * @return 收集源数据
     * @throws DataArchiverException 数据归档异常
     */
    protected abstract void schedule(Job job) throws DataArchiverException;


    /**
     * 校验源数据和目标数据
     * @param archiveTask 目标数据对象
     * @throws DataArchiverException 数据归档异常
     */
    protected abstract boolean proofreading(AmsArchiveTask archiveTask) throws DataArchiverException;


    /**
     * 处理目标数据
     * @param archiveTask 目标数据对象
     * @throws DataArchiverException 数据归档异常
     */
    protected abstract AmsArchiveTaskDetail verifyTask(AmsArchiveTaskDetail archiveTask) throws DataArchiverException;

    /**
     * 归档完成  清理本批次的数据清理
     *
     * @param archiveTask 目标数据对象
     * @throws DataArchiverException 数据归档异常
     */
    protected abstract void clear(AmsArchiveTask archiveTask) throws DataArchiverException;


    /**
     * 归档完成  清理本批次的数据清理
     *
     * @param archiveTask 目标数据对象
     * @throws DataArchiverException 数据归档异常
     */
    protected abstract void clearTaskDetail(AmsArchiveTaskDetail archiveTask) throws DataArchiverException;

    /**
     * 归档完成  结束事情
     *
     * @throws DataArchiverException 数据归档异常
     */
    protected abstract void finalize() throws DataArchiverException;

}
