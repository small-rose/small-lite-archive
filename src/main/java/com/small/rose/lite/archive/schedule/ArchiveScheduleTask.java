package com.small.rose.lite.archive.schedule;

import com.small.rose.lite.archive.core.life.DefaultDataArchiverLifecycle;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import com.small.rose.lite.archive.module.service.jpa.AmsArchiveJobConfigService;
import com.small.rose.lite.archive.utils.SmallUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ ArchiveTask ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 12:09
 * @Version: v1.0
 */
@Slf4j
@Component
public class ArchiveScheduleTask {

    @Autowired
    private DefaultDataArchiverLifecycle defaultDataArchiverLifecycle ;


    @Autowired
    private AmsArchiveJobConfigService archiveJobConfigService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void doArchive(){

        List<AmsArchiveJobConfig> jobConfigs = archiveJobConfigService.queryAllJobConfList();
        if (SmallUtils.isEmpty(jobConfigs)){
            log.info("没有找到可执行的归档配置！");
            return;
        }
        for (AmsArchiveJobConfig jobConfig : jobConfigs) {
            try {
                defaultDataArchiverLifecycle.execute(jobConfig);
            }catch (Exception e){
                e.printStackTrace();
                log.info("归档任务执行出错 {}" , ExceptionUtils.getStackTrace(e));
            }

        }

    }
}
