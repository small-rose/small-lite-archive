package com.small.rose.lite.archive.module.dao.jpa;

import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfParam;
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
public interface AmsArchiveJobConfParamRepository extends JpaRepository<AmsArchiveJobConfParam, Long> , JpaSpecificationExecutor<AmsArchiveJobConfParam> {

    @Query(value = "select t from AmsArchiveJobConfParam t where t.jobId = ?1 and t.ifValid = 1 order by t.paramOrder")
    List<AmsArchiveJobConfParam> findJobConfParamList(@Param("jobId") long jobId);
}
