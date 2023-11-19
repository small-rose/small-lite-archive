package com.small.rose.lite.archive.core;

import com.small.rose.lite.archive.core.job.Job;
import com.small.rose.lite.archive.core.job.Task;
import com.small.rose.lite.archive.core.job.TaskResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ TaskScheduler ] 接口说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/18 018 1:29
 * @Version: v1.0
 */
@Slf4j
@Component
public class JobScheduler {

    private ExecutorService executor = Executors.newFixedThreadPool(4);


    public void scheduleJob(Job job) {
        List<Task> taskList =  job.getArchiveTaskList();
        if (CollectionUtils.isEmpty(taskList)) {
            return;
        }
        List<TaskResult> failed = new ArrayList<>();
        for (Task task : taskList) {
            executor.submit(() -> {

                if (taskList.size() > 0) {
                    try {
                        TaskResult result = task.execute();

                        if (!result.isSuccess()) {
                            failed.add(result);
                        }
                    } catch (Exception e) {

                    } finally {

                    }
                }
            });
        }

        while (!executor.isTerminated()) {
            log.info("scheduled archive task is running ");
        }
        log.info("all scheduled archive task is successfully ");
    }

}
