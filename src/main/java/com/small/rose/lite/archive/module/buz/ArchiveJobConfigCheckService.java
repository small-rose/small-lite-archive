package com.small.rose.lite.archive.module.buz;

import com.small.rose.lite.archive.core.strategys.ArchiveModeCheckService;
import com.small.rose.lite.archive.emuns.ArchiveJobMode;
import com.small.rose.lite.archive.exception.ArchiveCheckException;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import com.small.rose.lite.archive.module.service.jdbc.ArchiveCheckService;
import com.small.rose.lite.archive.utils.SmallUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ ArchiveJobConfigCheckService ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 12:13
 * @Version: v1.0
 */
@Slf4j
@Service
public class ArchiveJobConfigCheckService {


    @Autowired
    private ArchiveCheckService archiveCheckService;
    @Autowired
    private ArchiveModeCheckService archiveModeCheckService;


    public boolean check(AmsArchiveJobConfig jobConfig) {
        boolean result = false;
        try {
            // check the field conf_source_table in the ARCHIVE_JOB_CONFIG table
            if (!SmallUtils.hasText(jobConfig.getSourceTable())) {
                log.info("归档作业配置检查，源库表[SOURCE_TABLE]不允许为空");
                throw new ArchiveCheckException("归档配置中归档源库表[SOURCE_TABLE]不允许为空");
            }
            //check the field job_mode in the ARCHIVE_JOB_CONFIG table
            if (!SmallUtils.hasText(jobConfig.getJobMode())) {
                log.info("归档作业配置检查，归档作业模式[JOB_MODE]不允许为空，可用模式有"+ArchiveJobMode.getAllModeName());
                throw new ArchiveCheckException("归档配置中归档源库表[JOB_MODE]不允许为空，可用模式有"+ArchiveJobMode.getAllModeName());
            }
            // check job mode enum
            ArchiveJobMode confMode = ArchiveJobMode.getModeCode(jobConfig.getJobMode());
            if (ArchiveJobMode.NULL_MODE.equals(confMode)) {
                log.info("归档作业配置检查，配置的作业模式【" + jobConfig.getJobMode() + "】暂不支持，可用模式有" + ArchiveJobMode.getAllModeName());
                throw new ArchiveCheckException("归档配置的目标表配置的归档模式【" + jobConfig.getJobMode() + "】暂不支持，可用模式有" + ArchiveJobMode.getAllModeName());
            }
            String s = ArchiveJobMode.ARCHIVE.name().equals(jobConfig.getJobMode()) ? ArchiveJobMode.ARCHIVE.getModeDesc() : ArchiveJobMode.DELETE.getModeDesc();
            //check the field conf_where in the ARCHIVE_JOB_CONFIG table
            if (!SmallUtils.hasText(jobConfig.getJobCondition())) {
                log.info("归档配置中" + s + "条件[JOB_CONDITION]不允许为空");
                throw new ArchiveCheckException("归档配置中" + s + "条件[JOB_CONDITION]不允许为空");
            }
            if (SmallUtils.hasText(jobConfig.getJobCondition()) && !jobConfig.getJobCondition().toLowerCase().startsWith("where")) {
                log.info("归档配置中" + s + "条件[JOB_CONDITION]必须以[WHERE]开头的SQL字符串");
                throw new ArchiveCheckException("归档配置中" + s + "条件[JOB_CONDITION]必须以[WHERE]开头的SQL字符串");
            }

            // check the table of source exists when CONF_MODE = DELETE
            result = archiveCheckService.checkExistsSourceTable(jobConfig);
            if (!result) {
                log.info("归档配置的源表在源数据库中不存在");
                throw new ArchiveCheckException("归档配置的源表[" + jobConfig.getSourceTable() + "]在源数据库中不存在");
            }
            // check the sql of source table can or not execute when CONF_MODE = DELETE
            archiveCheckService.checkSourceTableSql(jobConfig);

            // check the table of source exists when CONF_MODE = ARCHIVE
            result = archiveCheckService.checkExistsSourceTable(jobConfig);
            if (!result) {
                log.info(">>> 归档配置的源表在源数据库中不存在");
                throw new ArchiveCheckException("归档配置的源表[" + jobConfig.getSourceTable() + "]在源数据库中不存在");
            }
            // check the table of target exists when CONF_MODE = ARCHIVE
            result = archiveCheckService.checkExistsTargetTable(jobConfig);
            if (!result) {
                log.info(">>> 归档配置的目标表在归档库中不存在");
                throw new ArchiveCheckException("归档配置的目标表[" + jobConfig.getTargetTable() + "]在归档库中不存在");
            }
            // check the sql of source table can or not execute
            archiveCheckService.checkSourceTableSql(jobConfig);
            // check the sql of target table can or not execute
            archiveCheckService.checkTargetTableSql(jobConfig);
            // check some conf of ARCHIVE_MODE
            result = archiveModeCheckService.archiveBeforeCheck(jobConfig);
            if (!result) {
                log.info(">>> 归档配置的目标表[{}]在归档库中不存在, 本次不执行归档任务", jobConfig.getSourceTable());
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }
}
