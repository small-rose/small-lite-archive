package com.small.rose.lite.archive.module.buz;

import com.small.rose.lite.archive.core.strategys.convert.ArchiveJob2TaskConvert;
import com.small.rose.lite.archive.core.strategys.convert.ArchiveJob2TaskConvertFactory;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTask;
import com.small.rose.lite.archive.module.service.jpa.AmsArchiveTaskService;
import com.small.rose.lite.archive.utils.SmallUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
public class ArchiveJob2TaskService {

    @Autowired
    private ArchiveJob2TaskConvertFactory archiveJob2TaskConvertFactory;
    @Autowired
    private AmsArchiveTaskService amsArchiveTaskService;

    @Transactional
    public AmsArchiveTask convert(AmsArchiveJobConfig jobConfig) {
         List<AmsArchiveTask> taskFailedList = amsArchiveTaskService.findAmsArchiveTaskFailedList(jobConfig.getId());
        // 上次失败的继续执行
        if (SmallUtils.isNotEmpty(taskFailedList)){
           return taskFailedList.get(0) ;
        }
        ArchiveJob2TaskConvert strategyService = archiveJob2TaskConvertFactory.getArchiveStrategyService(jobConfig.getJobStrategy());
        AmsArchiveTask archiveTask = strategyService.jobConvertTask(jobConfig);
        return archiveTask;
    }
}
