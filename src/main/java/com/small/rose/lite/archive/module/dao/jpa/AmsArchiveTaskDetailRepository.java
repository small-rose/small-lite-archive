package com.small.rose.lite.archive.module.dao.jpa;

import com.small.rose.lite.archive.module.pojo.AmsArchiveTaskDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ AmsArchiveTaskDetailRepostory ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/18 018 23:39
 * @Version: v1.0
 */
@Repository
public interface AmsArchiveTaskDetailRepository extends JpaRepository<AmsArchiveTaskDetail, Long>, JpaSpecificationExecutor<AmsArchiveTaskDetail> {


    /**
     * 查对应批次号的任务，此时无状态控制
     *
     * @param jobId
     * @param jobBatchNo
     * @return
     */
    List<AmsArchiveTaskDetail> findByJobIdAndJobBatchNoOrderByTaskOrderAsc(Long jobId, String jobBatchNo);


    /**
     * 查对应批次号的任务 + 搬运状态
     *
     * @param jobId
     * @param jobBatchNo
     * @return
     */
    @Query( "select t from AmsArchiveTaskDetail t where  t.jobId= :jobId and t.jobBatchNo=:jobBatchNo and t.taskStatus=:taskStatus order by t.taskOrder" )
    List<AmsArchiveTaskDetail> findArchiveTaskDetailList(@Param( "jobId" ) Long jobId, @Param( "jobId" ) String jobBatchNo, @Param( "taskStatus" ) String taskStatus);

}
