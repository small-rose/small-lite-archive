package com.small.rose.lite.archive.core.job;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ Callback ] 接口说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/18 018 1:31
 * @Version: v1.0
 */
public interface Callback {


    void onTaskSuccess(Task task);

    void onTaskFailure(Task task, Throwable t);
}
