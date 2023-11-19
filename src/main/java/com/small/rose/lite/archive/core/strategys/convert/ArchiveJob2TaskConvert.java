package com.small.rose.lite.archive.core.strategys.convert;

import com.small.rose.lite.archive.emuns.ArchiveStrategyEnum;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTask;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ ArchiveJob2TaskConvert ] 接口说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 17:01
 * @Version: v1.0
 */
public interface ArchiveJob2TaskConvert {


    public ArchiveStrategyEnum getStrategyMode();

    public AmsArchiveTask jobConvertTask(AmsArchiveJobConfig conf);
}
