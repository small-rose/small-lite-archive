package com.small.rose.lite.archive.core.job;

import com.small.rose.lite.archive.module.pojo.AmsArchiveTask;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ Job ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/18 018 1:28
 * @Version: v1.0
 */
@Data
@Slf4j
@NoArgsConstructor
public class ArchiveJob implements Job {

    private String name;
    private AmsArchiveTask archiveTask;
    private List<Task> archiveTaskList;
    private ExecutorService executor = null;


    public ArchiveJob(AmsArchiveTask archiveTask) {
        this.name = archiveTask.getJobName();
        this.archiveTask = archiveTask;
        this.archiveTaskList = new CopyOnWriteArrayList<>();
        executor = Executors.newFixedThreadPool(4);
    }



    @Override
    public List<Task> getArchiveTaskList() {
        return archiveTaskList;
    }
}
