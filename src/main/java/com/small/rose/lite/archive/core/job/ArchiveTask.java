package com.small.rose.lite.archive.core.job;

import com.small.rose.lite.archive.module.pojo.AmsArchiveTaskDetail;
import lombok.Getter;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ ArichevTask ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/18 018 1:27
 * @Version: v1.0
 */
public class ArchiveTask implements Task{

    @Getter
    private AmsArchiveTaskDetail detailTask;
    private Callback callback;
    private TaskResult result ;

    public ArchiveTask(AmsArchiveTaskDetail detailTask, Callback callback) {
        this.detailTask = detailTask;
        this.callback = callback;
        this.result = TaskResult.builder().success(false).build();
    }

    @Override
    public TaskResult execute() {

        try {


            callback.onTaskSuccess(this);
            result.setSuccess(true);
        }catch (Exception e){
            callback.onTaskFailure(this, e);
            result.setSuccess(false);
            result.getVector().add(e);
        }
        return result;
    }

}
