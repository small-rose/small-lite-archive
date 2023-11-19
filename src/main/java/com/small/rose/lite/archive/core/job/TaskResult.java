package com.small.rose.lite.archive.core.job;

import lombok.Builder;
import lombok.Data;

import java.util.Vector;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ Task ] 接口说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/18 018 1:26
 * @Version: v1.0
 */
@Builder
@Data
public class TaskResult {


    private String taskId;
    private boolean success;
    private Vector<Throwable> vector = new Vector<>() ;


}
