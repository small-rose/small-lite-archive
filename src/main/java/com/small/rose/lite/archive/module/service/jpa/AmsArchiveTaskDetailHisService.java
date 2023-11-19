package com.small.rose.lite.archive.module.service.jpa;

import com.small.rose.lite.archive.module.dao.jpa.AmsArchiveTaskDetailHisRepository;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTaskDetailHis;
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
public class AmsArchiveTaskDetailHisService {

    @Autowired
    private AmsArchiveTaskDetailHisRepository archiveTaskDetailHisRepository ;

    public List<AmsArchiveTaskDetailHis> saveAll(List<AmsArchiveTaskDetailHis> taskDetailHisList){
        return archiveTaskDetailHisRepository.saveAll(taskDetailHisList);
    }

}
