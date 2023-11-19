package com.small.rose.lite.archive.module.service.jdbc;

import com.small.rose.lite.archive.constant.ArchiveConstant;
import com.small.rose.lite.archive.core.ArchiveContextHolder;
import com.small.rose.lite.archive.exception.ArchiveCheckException;
import com.small.rose.lite.archive.module.dao.jdbc.ArchiveCheckDao;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import com.small.rose.lite.archive.utils.SmallUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ ArchiveCheckService ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 17:33
 * @Version: v1.0
 */

@Slf4j
@Service
public class ArchiveCheckService {

    @Autowired
    private ArchiveCheckDao archiveCheckDao ;

    public boolean checkExistsSourceTable(AmsArchiveJobConfig jobConfig) {
        try {
            Long aLong = archiveCheckDao.checkExistsSourceTable(jobConfig);
            if (aLong > 0) {
                return true;
            }
        } catch (Exception e) {
            log.info("校验源库表失败：归档配置的源表可能在源数据库中不存在,或无权限查询[USER_TABLES]表: 错误提示 {}", e.getMessage());
            throw new ArchiveCheckException("校验源库表失败：归档配置的源表可能在源数据库中不存在,或无权限查询[USER_TABLES]表: 错误提示：" + ExceptionUtils.getStackTrace(e));
        }
        return false;
    }

    public boolean checkExistsTargetTable(AmsArchiveJobConfig jobConfig) {
        try {
            Long aLong = archiveCheckDao.checkExistsTargetTable(jobConfig);
            if (aLong > 0) {
                return true;
            }
        } catch (Exception e) {
            log.info("校验目标表失败：归档配置的目标表在归档库中不存在,或无权限查询[USER_TABLES]表: 错误提示 {}", e.getMessage());
            throw new ArchiveCheckException("校验目标表失败：归档配置的目标表在归档库中不存在,或无权限查询[USER_TABLES]表: 错误提示：" + ExceptionUtils.getStackTrace(e));
        }
        return false;
    }

    public boolean checkSourceTableSql(AmsArchiveJobConfig jobConfig) {
        Map<String, Object> archiveMap = new HashMap<>();
        try {
            Long count = archiveCheckDao.checkSourceTableSql(jobConfig);
            archiveMap.put(ArchiveConstant.COUNT_SOURCE, count);
            Map<String, Object> contextMap = ArchiveContextHolder.getArchiveMap();
            if (!SmallUtils.isEmpty(contextMap)) {
                contextMap.putAll(archiveMap);
            } else {
                contextMap = new HashMap<>();
                contextMap.putAll(archiveMap);
            }
            ArchiveContextHolder.setArchiveMap(contextMap);
            return true;

        } catch (Exception e) {
            log.info("校验归档查询SQL失败：" + e);
            throw new ArchiveCheckException("校验归档源库查询SQL失败: " + ExceptionUtils.getStackTrace(e));
        }
    }

    public boolean checkTargetTableSql(AmsArchiveJobConfig jobConfig) {
        Map<String, Object> archiveMap = new HashMap<>();
        try {
            Long count = archiveCheckDao.checkTargetTableSql(jobConfig);
            archiveMap.put(ArchiveConstant.COUNT_TARGET, count);
            Map<String, Object> contextMap = ArchiveContextHolder.getArchiveMap();
            if (!SmallUtils.isEmpty(contextMap)) {
                contextMap.putAll(archiveMap);
            } else {
                contextMap = new HashMap<>();
                contextMap.putAll(archiveMap);
            }
            ArchiveContextHolder.setArchiveMap(contextMap);
            return true;

        } catch (Exception e) {
            log.info("校验归档查询SQL失败：" + e);
            throw new ArchiveCheckException("校验归档目标库查询SQL失败: " + ExceptionUtils.getStackTrace(e));
        }
    }



    public Long queryTaskSqlCount(String dsName, String sql) {
        return archiveCheckDao.queryTaskSqlCount(dsName, sql);
    }


    public Date queryMinDate(String dsName, String sql) {
        return archiveCheckDao.queryMinDate(dsName, sql);
    }

    public Date queryForDate(String dsName, String value){
        return archiveCheckDao.queryForDate(dsName, value);
    }


    public long deleteArchivedData(String dsName, String taskDelSql) {
        return archiveCheckDao.deleteArchivedData(dsName, taskDelSql);
    }
}
