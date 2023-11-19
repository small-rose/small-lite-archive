package com.small.rose.lite.archive.module.dao.jdbc;

import com.small.rose.lite.archive.exception.ArchiveCheckException;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import com.small.rose.lite.archive.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ ArchiveDao ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 13:54
 * @Version: v1.0
 */
@Slf4j
@Repository
public class ArchiveCheckDao {


    @Autowired
    private JdbcTemplateFactory jdbcTemplateFactory;


    public Long checkExistsSourceTable(AmsArchiveJobConfig jobConfig) {
        try {
            String sql = " select count(*) from USER_TABLES a where a.table_name = '" + jobConfig.getSourceTable().toUpperCase() + "' ";
            Long aLong = jdbcTemplateFactory.getJdbcTemplate(jobConfig.getSourceDsName()).queryForObject(sql, Long.class);
            return aLong;
        } catch (Exception e) {
            log.info("校验源库表失败：归档配置的源表可能在源数据库中不存在,或无权限查询[USER_TABLES]表: 错误提示 {}", e.getMessage());
            throw new ArchiveCheckException("校验源库表失败：归档配置的源表可能在源数据库中不存在,或无权限查询[USER_TABLES]表: 错误提示：" + ExceptionUtils.getStackTrace(e));
        }
    }

    public Long checkExistsTargetTable(AmsArchiveJobConfig jobConfig) {
        Long aLong = 0L;
        try {
            String sql = " select count(*) from USER_TABLES a where a.table_name = '" + jobConfig.getTargetTable().toUpperCase() + "' ";
            aLong = jdbcTemplateFactory.getJdbcTemplate(jobConfig.getTargetDsName()).queryForObject(sql, Long.class);

        } catch (Exception e) {
            log.info("校验目标表失败：归档配置的目标表在归档库中不存在,或无权限查询[USER_TABLES]表: 错误提示 {}", e.getMessage());
            throw new ArchiveCheckException("校验目标表失败：归档配置的目标表在归档库中不存在,或无权限查询[USER_TABLES]表: 错误提示：" + ExceptionUtils.getStackTrace(e));
        }
        return aLong;
    }

    public Long checkSourceTableSql(AmsArchiveJobConfig jobConfig) {

        try {
            String archSql = SqlUtils.buildCheckSelectSql(jobConfig.getSourceTable(), jobConfig.getJobCondition());
            log.info(">>> source table count sql >>> " + archSql);
            Long count = jdbcTemplateFactory.getJdbcTemplate(jobConfig.getSourceDsName()).queryForObject(archSql, Long.class);
            return count;
        } catch (Exception e) {
            log.info("校验归档查询SQL失败：" + e);
            throw new ArchiveCheckException("校验归档源库查询SQL失败: " + ExceptionUtils.getStackTrace(e));
        }
    }

    public Long checkTargetTableSql(AmsArchiveJobConfig jobConfig) {

        try {
            String archSql = SqlUtils.buildCheckSelectSql(jobConfig.getTargetTable(), jobConfig.getJobCondition());
            log.info(">>> target table count sql >>> " + archSql);
            Long count = jdbcTemplateFactory.getJdbcTemplate(jobConfig.getSourceDsName()).queryForObject(archSql, Long.class);
            return count;
        } catch (Exception e) {
            log.info("校验归档查询SQL失败：" + e);
            throw new ArchiveCheckException("校验归档目标库查询SQL失败: " + ExceptionUtils.getStackTrace(e));
        }
    }


    public Long queryTaskSqlCount(String dsName, String sql) {
        Long count1 = jdbcTemplateFactory.getJdbcTemplate(dsName).queryForObject(sql, Long.class);
        return count1;
    }

    public Date queryMinDate(String dsName, String sql) {
        return jdbcTemplateFactory.getJdbcTemplate(dsName).queryForObject(sql, Date.class);
    }

    public Date queryForDate(String dsName, String value) {
        String sql = "select " + value + " from dual ";
        return jdbcTemplateFactory.getJdbcTemplate(dsName).queryForObject(sql, Date.class);
    }

    public long deleteArchivedData(String dsName, String taskDelSql) {
        long sum = 0;
        int[] count = jdbcTemplateFactory.getJdbcTemplate(dsName).batchUpdate(taskDelSql);
        for (int i : count) {
            sum += i;
        }
        return sum;
    }
}
