package com.small.rose.lite.archive.module.dao.jpa;

import com.small.rose.lite.archive.module.pojo.AmsArchiveTask;
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
public interface AmsArchiveTaskRepository extends JpaRepository<AmsArchiveTask, Long> , JpaSpecificationExecutor<AmsArchiveTask> {

    /**
     * HQL 查询 ,和上门的 JPA 方法定义同义
     *
     * @return
     */
    @Query( "select t from AmsArchiveTask t where t.jobId = :jobId and t.jobStatus LIKE '%FAILED' " )
    List<AmsArchiveTask> findAmsArchiveTaskFailedList(@Param("jobId") long jobId);
}
