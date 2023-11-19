package com.small.rose.lite.archive.core.life;

import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTask;
import com.small.rose.lite.archive.module.pojo.AmsArchiveTaskDetail;
import lombok.Data;

import java.util.List;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ DataArchiverContext ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 14:18
 * @Version: v1.0
 */
@Data
public class DataArchiverContext {

    protected AmsArchiveJobConfig jobConfig ;

    protected AmsArchiveTask archiveTask ;

    protected List<AmsArchiveTaskDetail> detailList ;

    public AmsArchiveJobConfig getJobConfig() {
        return jobConfig;
    }

    public void setJobConfig(AmsArchiveJobConfig jobConfig) {
        this.jobConfig = jobConfig;
    }

    public AmsArchiveTask getArchiveTask() {
        return archiveTask;
    }

    public void setArchiveTask(AmsArchiveTask archiveTask) {
        this.archiveTask = archiveTask;
    }

    public List<AmsArchiveTaskDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<AmsArchiveTaskDetail> detailList) {
        this.detailList = detailList;
    }
}
