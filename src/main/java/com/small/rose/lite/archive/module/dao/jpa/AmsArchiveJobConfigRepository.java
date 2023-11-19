package com.small.rose.lite.archive.module.dao.jpa;

import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
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
public interface AmsArchiveJobConfigRepository extends JpaRepository<AmsArchiveJobConfig, Long>, JpaSpecificationExecutor<AmsArchiveJobConfig> {


    /**
     * 查激活的生效配置
     *   jpa接口规范查询
     * @param ifValid
     * @return
     */
    List<AmsArchiveJobConfig> findByIfValidEqualsOrderByJobPriorityDesc(Long ifValid);

    /**
     * HQL 查询 ,和上门的 JPA 方法定义同义
     *
     * @return
     */
    @Query( "select t from AmsArchiveJobConfig t where t.ifValid = 1 order by t.jobPriority desc " )
    List<AmsArchiveJobConfig> findJobConfigList();

    /**
     * 原生 SQL 查询 ,和上门的 JPA 方法定义同义
     *
     * @return
     */
    @Query( value = "select t from ams_archive_job_config t where t.if_valid = 1 order by t.job_priority desc ", nativeQuery = true )
    List<AmsArchiveJobConfig> findUsedJobConfigList();
}
