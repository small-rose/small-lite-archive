package com.small.rose.lite.archive.core.life;

import com.small.rose.lite.archive.exception.DataArchiverException;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ DataArchiverLifecycle ] 接口说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 12:37
 * @Version: v1.0
 */
public interface DataArchiverLifecycle {

    /**
     * 执行生命周期
     * @throws DataArchiverException 数据归档异常
     */
    void execute(AmsArchiveJobConfig jobConfig) throws DataArchiverException;
}
