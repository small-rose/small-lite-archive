package com.small.rose.lite.archive.utils;

import com.small.rose.lite.archive.emuns.ArchiveParamTypeEnum;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfParam;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Project : small-db-archive
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2023/11/12 012 0:22
 * @version: v1.0
 */
public class SqlUtils {

    public static String buildConditionSql(String sql, AmsArchiveJobConfig jobConfig, Date column) {
        StringBuffer sb = new StringBuffer();
        if (sql.toUpperCase().startsWith("WHERE")) {
            sb.append(sql);
        } else {
            sb.append("WHERE ").append(sql);
        }
        String format = SmallDateUtils.format(column, SmallDateUtils.YYYY_MM_DD);
        sb.toString().replace(jobConfig.getJobColumns(), " <= date'" + format + "' ");
        return sb.toString();
    }

    public static String buildCheckSelectSql(String tableName, String whereStr) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT COUNT(1) FROM ( ");
        sb.append("SELECT DISTINCT * FROM ").append(tableName).append(" ").append(whereStr);
        sb.append(")  ");
        return sb.toString();
    }

    public static String buildAppendSelectSql(String tableName, String whereSql) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DISTINCT * FROM").append(tableName).append(" ").append(whereSql);
        return sb.toString();
    }

    public static String buildAppendDeleteSql(String tableName, String whereSql) {
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM ").append(tableName).append(" ").append(whereSql);
        return sb.toString();
    }

    public static String buildInsertSql(String tableName, Map<String, Object> rowData) {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ").append(tableName).append(" (");
        List<String> columns = new ArrayList<>(rowData.keySet());
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(columns.get(i));
        }
        sb.append(") VALUES (");
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("?");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 组装SQL条件
     *
     * @param sql
     * @param paramList
     * @return
     */
    public static String buildTaskSql(String sql, List<AmsArchiveJobConfParam> paramList) {
        if (CollectionUtils.isEmpty(paramList)) {
            return sql;
        }
        String tmp = sql;
        for (AmsArchiveJobConfParam param : paramList) {
            if (ArchiveParamTypeEnum.DATE.name().equalsIgnoreCase(param.getParamType())) {
                param.setParamValue(" date'".concat(param.getParamValue()).concat("' "));
            }
            sql.replace(param.getParamName(), param.getParamValue());

        }
        return tmp;
    }

    /**
     * SELECT *
     * FROM (
     * SELECT a.*, ROWNUM r
     * FROM my_table a
     * WHERE ROWNUM <= 30
     * )
     * WHERE r >= 20;
     *
     * @param jobConfig
     * @param sql
     * @return
     */
    public static String buildAppendSelectSqlPages(AmsArchiveJobConfig jobConfig, String sql, long start, long end) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT T.* FROM ( SELECT  X.*, ROWNUM R FROM  (");
        sb.append("SELECT DISTINCT * FROM").append(jobConfig.getSourceTable()).append(" ").append(sql);
        sb.append(") X  WHERE X.R > ").append(start);
        sb.append(" ) T WHERE T.R <= ").append(end);
        return sb.toString();
    }

    /**
     * 组装分页删除
     *
     * @param jobConfig
     * @param sql
     * @param numEnd
     * @return
     */
    public static String buildAppendDeleteSqlPages(AmsArchiveJobConfig jobConfig, String sql, long numEnd) {
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE * FROM").append(jobConfig.getSourceTable()).append(" ").append(sql);
        sb.append(" AND ROWNUM <= ").append(numEnd);
        return sb.toString();
    }
}
