package com.small.rose.lite.archive.module.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ AmsArchiveJobConfParam ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 17:23
 * @Version: v1.0
 */
@Data
@Entity
@Table(name = "AMS_ARCHIVE_JOB_CONF_PARAM")
public class AmsArchiveJobConfParam implements Serializable {


    @Id
    @SequenceGenerator( name = "ams_archive_job_conf_param_id", sequenceName = "AMS_ARCHIVE_JOB_CONF_PARAM_SEQ" ,allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "ams_archive_job_conf_param_id" )
    private Long id;


    @Column(name = "JOB_ID")
    private Long jobId;


    @Column(name = "PARAM_PK")
    private String paramPk;


    @Column(name = "PARAM_NAME")
    private String paramName;


    @Column(name = "PARAM_TYPE")
    private String paramType;


    @Column(name = "PARAM_VALUE")
    private String paramValue;


    @Column(name = "PARAM_EXT_VAL")
    private String paramExtVal;


    @Column(name = "PARAM_DESC")
    private String paramDesc;


    @Column(name = "PARAM_ORDER")
    private Long paramOrder;


    @Column(name = "CREATE_TIME")
    private Date createTime;


    @Column(name = "UPDATE_TIME")
    private Date updateTime;


    @Column(name = "IF_VALID")
    private int ifValid;


    @Column(name = "EXT1")
    private String ext1;


    @Column(name = "EXT2")
    private String ext2;


    @Transient
    private Date paramValueDate ;
}

