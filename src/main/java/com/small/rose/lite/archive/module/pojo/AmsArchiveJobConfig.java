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
@Table(name = "AMS_ARCHIVE_JOB_CONFIG")
public class AmsArchiveJobConfig implements Serializable {

    @Id
    @Column( name = "ID" )
    @SequenceGenerator( name = "conf_id", sequenceName = "conf_id_seq" ,allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "conf_id" )
    private long id;

    @Column( name = "JOB_NAME" )
    private String jobName;

    @Column( name = "SOURCE_DS_NAME"  )
    private String sourceDsName;

    @Column( name = "SOURCE_TABLE"  )
    private String sourceTable;

    @Column( name = "TARGET_DS_NAME"  )
    private String targetDsName;


    @Column(name = "TARGET_TABLE")
    private String targetTable;


    @Column(name = "JOB_CONDITION")
    private String jobCondition;

    @Column( name = "JOB_MODE"  )
    private String jobMode;

    /**
     *  归档策略 ARCHIVE_DATE_PAGE 日期+分页  ARCHIVE_PK_NUMBER_PAGE 主键范围+分页、ARCHIVE_PK_STRING_PAGE 主键字符串模式
     *  删除策略 DELETE_DATE_PAGE 日期+分页删除模式  DELETE_PK_NUMBER_PAGE  主键范围+分页删除模式、
     */
    @Column( name = "JOB_STRATEGY"  )
    private String jobStrategy ;

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

    /**
     * (归档/删除)批任务数量大小
     */
    @Column(name = "JOB_PAGE_NUM")
    private long jobPageNum;

    /**
     * (归档/删除)批次单任务处理页大小
     */
    @Column(name = "JOB_PAGE_SIZE")
    private long jobPageSize;

    /**
     * 归档/删除任务优先级
     */
    @Column(name = "JOB_PRIORITY")
    private Long jobPriority;


    @Column(name = "CREATE_TIME")
    private Date createTime;


    @Column(name = "UPDATE_TIME")
    private Date updateTime;


    @Column(name = "IF_VALID")
    private long ifValid;


    @Column(name = "EXT1")
    private String ext1;


    @Column(name = "EXT2")
    private String ext2;


}
