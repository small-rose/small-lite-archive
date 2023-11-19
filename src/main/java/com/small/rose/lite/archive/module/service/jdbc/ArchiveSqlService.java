package com.small.rose.lite.archive.module.service.jdbc;

import com.small.rose.lite.archive.emuns.ArchiveParamTypeEnum;
import com.small.rose.lite.archive.exception.ArchiveCheckException;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfParam;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTaskDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2023/11/12 012 0:58
 * @version: v1.0
 */
@Slf4j
@Component
public class ArchiveSqlService {


    @Autowired
    private ArchiveCheckService archiveCheckService;

    public List<AmsArchiveJobConfParam> calculateDateParams(String dsName, List<AmsArchiveJobConfParam> paramList) {

        if (CollectionUtils.isEmpty(paramList)) {
            return Collections.emptyList();
        }
        AmsArchiveJobConfParam param = null;
        List<AmsArchiveJobConfParam> resultList = new ArrayList<>();
        for (AmsArchiveJobConfParam tmp : paramList) {
            param = new AmsArchiveJobConfParam();
            BeanUtils.copyProperties(tmp, param);
            if (ArchiveParamTypeEnum.DATE.name().equalsIgnoreCase(tmp.getParamType())) {
                Date date = archiveCheckService.queryForDate(dsName, tmp.getParamValue());
                param.setParamValue(new SimpleDateFormat(tmp.getParamExtVal()).format(date));
                param.setParamValueDate(date);
            }
            resultList.add(param);
        }
        return resultList;
    }

    public long queryTaskSqlCount(String sql, AmsArchiveTaskDetail task) {
        try {
            long nums = archiveCheckService.queryTaskSqlCount(task.getTargetDsName(), sql);
            return nums ;
        } catch (Exception e) {
            throw new ArchiveCheckException("校验任务SQL执行失败", e);
        }
    }
    public Date queryMinDateForColumn(AmsArchiveJobConfig jobConfig) {
        String sql = "select min(" + jobConfig.getJobColumns() + ") from " + jobConfig.getSourceTable();
        Date minDate = archiveCheckService.queryMinDate(jobConfig.getSourceDsName(), sql);
        return minDate ;
    }

    public Date queryDateLineDate(AmsArchiveJobConfig jobConfig) {
        return archiveCheckService.queryForDate(jobConfig.getSourceDsName(), jobConfig.getJobDeadLine());
    }

    public AmsArchiveJobConfParam queryMinDate(AmsArchiveJobConfig jobConfig) {
        AmsArchiveJobConfParam param = new AmsArchiveJobConfParam();
        param.setJobId(jobConfig.getId());
        param.setIfValid(1);
        param.setParamName(jobConfig.getJobColumns());
        param.setParamPk(jobConfig.getJobColumns());
        param.setParamType("Date");
        param.setParamExtVal("yyyy-MM-dd");
        param.setExt1("AUTO");
        try {
            String sql = "select min(" + jobConfig.getJobColumns() + ") from " + jobConfig.getSourceTable();
            Date minDate = archiveCheckService.queryMinDate(jobConfig.getSourceDsName(), sql);
            param.setParamValue(new SimpleDateFormat("yyyy-MM-dd").format(minDate));
            //自动生成一个Param 并保存到数据库
            return param;
        } catch (Exception e) {
            throw new ArchiveCheckException("校验任务SQL执行失败", e);
        }
    }


    public Long queryVerifySize(AmsArchiveTaskDetail taskDetail) {
        return archiveCheckService.queryTaskSqlCount(taskDetail.getTargetDsName(), taskDetail.getTaskSelSql());
    }

    @Transactional
    public long deleteArchivedData(AmsArchiveTaskDetail taskDetail) {
        return archiveCheckService.deleteArchivedData(taskDetail.getTargetDsName(), taskDetail.getTaskDelSql());
    }
}
