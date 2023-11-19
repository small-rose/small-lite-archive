package com.small.rose.lite.archive.core.strategys.check;

import com.small.rose.lite.archive.emuns.ArchiveStrategyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ ArchiveJobModeCheckFactory ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 14:44
 * @Version: v1.0
 */
@Component
public class ArchiveJobModeCheckFactory {

    @Autowired
    private List<ArchiveJobModeCheck> jobModeCheckList ;


    public ArchiveJobModeCheck getArchiveModeCheckStrategy(ArchiveStrategyEnum strategyEnum) {
        return jobModeCheckList.stream().filter(s->(strategyEnum.name().equalsIgnoreCase(s.getArchiveStrategy().name()))).findFirst().get();
    }

}
