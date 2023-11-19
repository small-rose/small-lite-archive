package com.small.rose.lite.archive.core.strategys.check;

import com.small.rose.lite.archive.emuns.ArchiveStrategyEnum;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ ArchiveJobModeCheck ] 接口说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 14:48
 * @Version: v1.0
 */
public interface ArchiveJobModeCheck {


    public ArchiveStrategyEnum getArchiveStrategy();

    public boolean check(AmsArchiveJobConfig jobConfig);
}
