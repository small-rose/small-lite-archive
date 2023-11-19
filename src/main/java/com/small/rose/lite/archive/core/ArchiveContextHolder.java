package com.small.rose.lite.archive.core;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ ArchiveContextHolder ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 14:27
 * @Version: v1.0
 */
@Slf4j
public class ArchiveContextHolder {

    private static final ThreadLocal<String> archiveSqlHolder = new ThreadLocal<>();

    private static final ThreadLocal<Map<String,Object>> archiveMapHolder = new ThreadLocal<>();


    public static void setArchiveSql(String sql) {
        archiveSqlHolder.set(sql);
    }

    public static String getArchiveSql() {
        String key = archiveSqlHolder.get();
        return  key;
    }

    public static void setArchiveMap(Map<String,Object> archiveMap) {
        archiveMapHolder.set(archiveMap);
    }

    public static Map<String,Object> getArchiveMap() {
        return  archiveMapHolder.get();
    }
}
