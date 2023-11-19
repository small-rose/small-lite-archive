package com.small.rose.lite.archive.core.strategys;


import com.small.rose.lite.archive.core.strategys.check.ArchiveJobModeCheck;
import com.small.rose.lite.archive.core.strategys.check.ArchiveJobModeCheckFactory;
import com.small.rose.lite.archive.emuns.ArchiveStrategyEnum;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2023/11/11 011 23:05
 * @version: v1.0
 */

@Service
public class ArchiveModeCheckService {

    @Autowired
    private ArchiveJobModeCheckFactory archiveJobModeCheckFactory;



    public boolean archiveBeforeCheck(AmsArchiveJobConfig conf){
        ArchiveJobModeCheck archiveModeCheck = archiveJobModeCheckFactory.getArchiveModeCheckStrategy(ArchiveStrategyEnum.getStrategy(conf.getJobStrategy()));
        return archiveModeCheck.check(conf);
    }




}
