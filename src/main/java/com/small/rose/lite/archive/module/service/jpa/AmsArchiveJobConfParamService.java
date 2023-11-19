package com.small.rose.lite.archive.module.service.jpa;

import com.small.rose.lite.archive.module.dao.jpa.AmsArchiveJobConfParamRepository;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ AmsArchiveJobConfParamService ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 17:28
 * @Version: v1.0
 */

@Slf4j
@Service
public class AmsArchiveJobConfParamService {

    @Autowired
    private AmsArchiveJobConfParamRepository amsArchiveJobConfParamRepository ;


    public List<AmsArchiveJobConfParam> findJobConfParamListByJobId(Long jobId){
        return amsArchiveJobConfParamRepository.findJobConfParamList(jobId);
    }
}
