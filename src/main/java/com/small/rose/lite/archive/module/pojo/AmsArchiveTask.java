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
 * @Description: [ AmsArchiveTask ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/18 018 23:28
 * @Version: v1.0
 */
@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name = "AMS_ARCHIVE_TASK")
public class AmsArchiveTask implements Serializable {


    @Id
    @SequenceGenerator( name = "ams_archive_task_id", sequenceName = "AMS_ARCHIVE_TASK_SEQ" ,allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "ams_archive_task_id" )
    private long id;


    @Column(name = "JOB_ID")
    private long jobId;

    @Column(name = "JOB_NAME")
    private String jobName;


    @Column(name = "JOB_BATCH_NO")
    private String jobBatchNo;


    @Column(name = "JOB_STATUS")
    private String jobStatus;


    @Column( name = "JOB_DEAD_LINE"  )
    private String jobDeadLine ;

    /**
     * 搭配主键列使用
     */
    @Column( name = "JOB_RANGE_ID"  )
    private String jobRangeId ;

    @Column( name = "JOB_LIKE_ID"  )
    private String jobLikeId ;


    @Column(name = "JOB_TABLE_PK")
    private String jobTablePk;

    /**
     * 归档日期查询列
     */
    @Column(name = "JOB_COLUMNS")
    private String jobColumns;


    @Column(name = "DO_DISTINCT")
    private long doDistinct;

    @Column(name = "TOTAL_EXPECT_SIZE")
    private Long totalExpectSize;


    @Column(name = "TARGET_ARCHIVED_SIZE")
    private Long targetArchivedSize;


    @Column(name = "TOTAL_ACTUAL_SIZE")
    private Long totalActualSize;


    @Column(name = "JOB_START_TIME")
    private Date jobStartTime;


    @Column(name = "JOB_END_TIME")
    private Date jobEndTime;


    @Column(name = "EXT1")
    private String ext1;


    @Column(name = "EXT2")
    private String ext2;


}