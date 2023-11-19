package com.small.rose.lite.archive.module.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ ArchiveDetailTask ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/18 018 1:34
 * @Version: v1.0
 */

@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name = "AMS_ARCHIVE_TASK_DETAIL")
public class AmsArchiveTaskDetail implements Serializable {


    @Id
    @SequenceGenerator( name = "ams_archive_task_detail_id", sequenceName = "AMS_ARCHIVE_TASK_DETAIL_SEQ" ,allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "ams_archive_task_detail_id" )
    private long id;


    @Column(name = "TASK_ID")
    private long taskId;


    @Column(name = "JOB_ID")
    private long jobId;


    @Column(name = "JOB_BATCH_NO")
    private String jobBatchNo;


    @Column(name = "SOURCE_DS_NAME")
    private String sourceDsName;


    @Column(name = "SOURCE_TABLE")
    private String sourceTable;


    @Column(name = "TARGET_DS_NAME")
    private String targetDsName;


    @Column(name = "TARGET_TABLE")
    private String targetTable;


    @Column(name = "TASK_SEL_SQL")
    private String taskSelSql;


    @Column(name = "TASK_DEL_SQL")
    private String taskDelSql;


    @Column(name = "TASK_STATUS")
    private String taskStatus;


    @Column(name = "TASK_ORDER")
    private int taskOrder;


    @Column(name = "EXPECT_SIZE")
    private Long expectSize;


    @Column(name = "ACTUAL_SIZE")
    private Long actualSize;


    @Column(name = "TASK_START")
    private Date taskStart;


    @Column(name = "TASK_END")
    private Date taskEnd;


    @Column(name = "VERIFY_SIZE")
    private Long verifySize;


    @Column(name = "VERIFY_START")
    private Date verifyStart;


    @Column(name = "VERIFY_END")
    private Date verifyEnd;


    @Column(name = "DELETE_SIZE")
    private Long deleteSize;


    @Column(name = "DELETE_START")
    private Date deleteStart;


    @Column(name = "DELETE_END")
    private Date deleteEnd;


    @Column(name = "ERROR_INFO")
    private String errorInfo;


    @Column(name = "CREATE_TIME")
    private Date createTime;


    @Column(name = "UPDATE_TIME")
    private Date updateTime;


    @Column(name = "EXT1")
    private String ext1;


    @Column(name = "EXT2")
    private String ext2;


}
