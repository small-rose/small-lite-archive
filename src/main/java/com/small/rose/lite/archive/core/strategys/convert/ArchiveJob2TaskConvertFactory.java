package com.small.rose.lite.archive.core.strategys.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ ArchiveJob2TaskFactory ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 21:35
 * @Version: v1.0
 */
@Component
public class ArchiveJob2TaskConvertFactory {

    @Autowired
    private List<ArchiveJob2TaskConvert> job2TaskConvertList ;


    public ArchiveJob2TaskConvert getArchiveStrategyService(String strategyName) {
        return job2TaskConvertList.stream().filter(s->(strategyName.equalsIgnoreCase(s.getStrategyMode().name()))).findFirst().get();
    }

}
