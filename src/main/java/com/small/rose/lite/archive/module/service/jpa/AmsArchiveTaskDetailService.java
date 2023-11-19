package com.small.rose.lite.archive.module.service.jpa;

import com.small.rose.lite.archive.emuns.ArchiveJobStatusEnum;
import com.small.rose.lite.archive.module.dao.jpa.AmsArchiveTaskDetailRepository;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTask;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTaskDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class AmsArchiveTaskDetailService {

    @Autowired
    private AmsArchiveTaskDetailRepository amsArchiveTaskDetailRepository;


    public List<AmsArchiveTaskDetail> findPrepareTaskDetailList(AmsArchiveTask amsArchiveTask){
        return amsArchiveTaskDetailRepository.findByJobIdAndJobBatchNoOrderByTaskOrderAsc(amsArchiveTask.getJobId(), amsArchiveTask.getJobBatchNo());
    }


    public AmsArchiveTaskDetail save(AmsArchiveTaskDetail archiveTaskDetail){
        return amsArchiveTaskDetailRepository.save(archiveTaskDetail);
    }

    @Transactional
    public AmsArchiveTaskDetail saveAndFlush(AmsArchiveTaskDetail archiveTaskDetail){
        return amsArchiveTaskDetailRepository.saveAndFlush(archiveTaskDetail);
    }

    public List<AmsArchiveTaskDetail> saveAll(List<AmsArchiveTaskDetail> taskDetailList){
        return amsArchiveTaskDetailRepository.saveAll(taskDetailList);
    }

    public List<AmsArchiveTaskDetail> findTaskDetailList(AmsArchiveTask archiveTask, ArchiveJobStatusEnum statusEnum) {
        return amsArchiveTaskDetailRepository.findArchiveTaskDetailList(archiveTask.getJobId(), archiveTask.getJobBatchNo(), statusEnum.getStatus());
    }


}
