package com.small.rose.lite.archive.module.dao.jpa;

import com.small.rose.lite.archive.module.pojo.AmsArchiveTaskDetailHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ AmsArchiveTaskDetailRepostory ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/18 018 23:39
 * @Version: v1.0
 */
@Repository
public interface AmsArchiveTaskDetailHisRepository extends JpaRepository<AmsArchiveTaskDetailHis, Long>, JpaSpecificationExecutor<AmsArchiveTaskDetailHis> {


}
