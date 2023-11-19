package com.small.rose.lite.archive.core.strategys.convert;

import com.small.rose.lite.archive.constant.ArchiveConstant;
import com.small.rose.lite.archive.core.ArchiveContextHolder;
import com.small.rose.lite.archive.emuns.ArchiveJobStatusEnum;
import com.small.rose.lite.archive.emuns.ArchiveStrategyEnum;
import com.small.rose.lite.archive.exception.ArchiveConvertException;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTask;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTaskDetail;
import com.small.rose.lite.archive.module.service.jdbc.ArchiveSqlService;
import com.small.rose.lite.archive.module.service.jpa.AmsArchiveTaskDetailService;
import com.small.rose.lite.archive.module.service.jpa.AmsArchiveTaskService;
import com.small.rose.lite.archive.utils.SmallUtils;
import com.small.rose.lite.archive.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ DefaultPageConvertStrategy ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 17:00
 * @Version: v1.0
 */
@Slf4j
@Component
public class ArchiveByDatePageConvertStrategy implements ArchiveJob2TaskConvert{


    @Autowired
    private AmsArchiveTaskService amsArchiveTaskService ;
    @Autowired
    private AmsArchiveTaskDetailService amsArchiveTaskDetailService ;
    @Autowired
    private ArchiveSqlService archiveSqlService;

    private static Integer  DEFAULT_MAX_PAGE_NUM = 100;
    private static Long DEFAULT_PAGE_SIZE = 3000l;



    @Override
    public ArchiveStrategyEnum getStrategyMode() {
        return ArchiveStrategyEnum.ARCHIVE_DATE_PAGE;
    }

    @Override
    @Transactional(rollbackFor = ArchiveConvertException.class)
    public AmsArchiveTask jobConvertTask(AmsArchiveJobConfig jobConfig) {
        log.info(">>> 归档作业转换任务开始"+jobConfig.getSourceTable());
        try {
            // 再次校验是否有数据需要归档
            Map<String, Object> archiveMap = ArchiveContextHolder.getArchiveMap();
            Long sourceCount = archiveMap.get(ArchiveConstant.COUNT_SOURCE)!=null ? (long)archiveMap.get(ArchiveConstant.COUNT_SOURCE) : -1;
            if (sourceCount <= 0){
                log.info("本次无可归档数据，不生成归档任务！");
                return null;
            }
            // 查到了待归档的最小日期
            Date minDate = archiveSqlService.queryMinDateForColumn(jobConfig);
            //Date dateLine = archiveSqlService.queryDateLineDate(jobConfig);


            // 提取SQL条件
            String conditionSql = jobConfig.getJobCondition();

            String batchNo = jobConfig.getSourceTable().toUpperCase().concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            Long pageNums = jobConfig.getJobPageNum()==0? jobConfig.getJobPageNum() : DEFAULT_MAX_PAGE_NUM ;
            Long pageSize =jobConfig.getJobPageSize()==0? jobConfig.getJobPageSize() : DEFAULT_PAGE_SIZE ;

            String selectSql = "";
            String deleteSel = "";

            //计算分页,并准备保证任务
            double pageSizeDb = jobConfig.getJobPageSize();
            long pageNo = 1;
            if (sourceCount > pageSize) {
                pageNo = (long) Math.ceil(sourceCount / pageSizeDb);
            }
            // 一批次任务超过最大值时取最大值
            if (pageNo > pageNums){
                log.info(">>> 警告 >> 本次预测任务量 {} ,配置的任务量为 {}", pageNo, pageNums);
            }


            List<AmsArchiveTaskDetail> taskList = new ArrayList<>();
            AmsArchiveTaskDetail taskDetail = null;
            long numStart = 1 ;
            long numEnd = 0 ;
            long taskPageSize = jobConfig.getJobPageSize();
            String sql = "";
            for (int x = 1; x <= pageNo; x++) {
                if (x>1){
                    numStart = numEnd ;
                }
                numEnd = x * taskPageSize ;
                // 拼接date类型
                sql = SqlUtils.buildConditionSql(conditionSql, jobConfig, minDate);
                selectSql = SqlUtils.buildAppendSelectSqlPages(jobConfig, sql, numStart, numEnd);
                deleteSel = SqlUtils.buildAppendDeleteSqlPages(jobConfig, sql, numEnd);
                taskDetail = new AmsArchiveTaskDetail();
                taskDetail.setJobId(jobConfig.getId());
                taskDetail.setJobBatchNo(batchNo);
                taskDetail.setCreateTime(new Date());
                taskDetail.setTaskOrder(x);
                taskDetail.setSourceTable(jobConfig.getSourceTable());
                taskDetail.setSourceDsName(jobConfig.getSourceDsName());
                taskDetail.setTargetTable(jobConfig.getTargetTable());
                taskDetail.setTargetDsName(jobConfig.getTargetDsName());
                taskDetail.setTaskSelSql(selectSql);
                taskDetail.setTaskDelSql(deleteSel);
                taskDetail.setTaskStatus(ArchiveJobStatusEnum.PREPARE.getStatus());
                taskDetail.setCreateTime(new Date());
                long nums = archiveSqlService.queryTaskSqlCount(sql, taskDetail);
                taskDetail.setExpectSize(nums);
                taskList.add(taskDetail);
            }
            List<AmsArchiveTaskDetail> total = amsArchiveTaskDetailService.saveAll(taskList);
            if (SmallUtils.isNotEmpty(total) && pageNo != total.size()) {
                throw new ArchiveConvertException("归档作业转换任务异常");
            }
            AmsArchiveTask task = new AmsArchiveTask();
            task.setJobId(jobConfig.getId());
            task.setJobBatchNo(batchNo);
            task.setTotalExpectSize(sourceCount);
            task.setJobStatus("");
            task.setJobStatus(ArchiveJobStatusEnum.PREPARE.getStatus());
            amsArchiveTaskService.saveAndFlush(task);
            log.info(">>> 归档作业转换任务成功");
            return task ;
        } catch (Exception e) {
            String ex = ExceptionUtils.getStackTrace(e);
            log.info(">>> 归档作业转换任务异常：" + ex);
            throw new ArchiveConvertException("归档作业转换任务异常");
        }finally {
            log.info(">>> 归档作业转换任务结束");
        }
     }


}
