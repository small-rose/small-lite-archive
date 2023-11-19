package com.small.rose.lite.archive.module.dao.jdbc;

import com.small.rose.lite.archive.exception.ArchiveCheckException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ JdbcTemplateFactory ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 14:10
 * @Version: v1.0
 */
@Component
public class JdbcTemplateFactory {

    @Autowired
    private JdbcTemplate masterJdbcTemplate ;

    @Autowired
    private JdbcTemplate archiveJdbcTemplate ;

    public JdbcTemplate getJdbcTemplate(String dsName){
        if (!StringUtils.hasText(dsName)){
            dsName = "master";
        }
        if ("master".equalsIgnoreCase(dsName)){
            return masterJdbcTemplate;
        }else if ("archive".equalsIgnoreCase(dsName)){
            return archiveJdbcTemplate;
        }
        throw new ArchiveCheckException("无法识别的数据源名称");
    }

}
