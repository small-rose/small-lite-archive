package com.small.rose.lite.archive.module.service.jpa;

import com.small.rose.lite.archive.module.dao.jpa.AmsArchiveTaskRepository;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ AmsArchiveTaskService ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/18 018 23:47
 * @Version: v1.0
 */

@Slf4j
@Service
public class AmsArchiveTaskService {


    @Autowired
    private AmsArchiveTaskRepository amsArchiveTaskRepository ;


    public AmsArchiveTask save(AmsArchiveTask archiveTask){
        return amsArchiveTaskRepository.save(archiveTask);
    }


    public AmsArchiveTask saveAndFlush(AmsArchiveTask archiveTask){
        return amsArchiveTaskRepository.saveAndFlush(archiveTask);
    }


    public List<AmsArchiveTask> findAmsArchiveTaskFailedList(long  jobId){
        return amsArchiveTaskRepository.findAmsArchiveTaskFailedList(jobId);
    }
}
