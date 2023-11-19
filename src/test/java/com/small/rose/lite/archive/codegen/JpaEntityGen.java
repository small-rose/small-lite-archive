package com.small.rose.lite.archive.codegen;

import com.small.rose.lite.archive.base.SmallLiteArchiveAppTests;
import com.small.rose.lite.archive.module.pojo.TbColumn;
import com.small.rose.lite.archive.utils.CamelCaseUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ JpaEntityGen ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/18 018 14:53
 * @Version: v1.0
 */
public class JpaEntityGen extends SmallLiteArchiveAppTests {

    @Autowired
    JdbcTemplate masterJdbcTemplate;

    private static Map<String, String> sqlTypeMap = new HashMap<>();

    static {
        sqlTypeMap.put("VARCHAR", "String");
        sqlTypeMap.put("VARCHAR2", "String");
        sqlTypeMap.put("NUMBER", "Long");
        sqlTypeMap.put("DATE", "Date");
        sqlTypeMap.put("INT", "Long");
        // 其他类型映射
    }

    @Test
    public void test_codegen(){

        String sql = "SELECT  TC.TABLE_NAME,  TC.COLUMN_NAME, DATA_TYPE\n" +
                "FROM USER_TAB_COLUMNS TC\n" +
                "WHERE  TC.TABLE_NAME in ('AMS_ARCHIVE_JOB_CONF_PARAM','AMS_ARCHIVE_JOB_CONFIG','AMS_ARCHIVE_TASK','AMS_ARCHIVE_TASK_DETAIL')\n" +
                "ORDER BY TC.TABLE_NAME  , TC.COLUMN_ID ASC ";

        List<TbColumn> tbColumns = masterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TbColumn.class));
        Map<String, List<TbColumn>> collect = tbColumns.stream().collect(Collectors.groupingBy(TbColumn::getTableName));
        for (String tableName : collect.keySet()) {
            List<TbColumn> columnList = collect.get(tableName);
            System.out.println("--------------------------------"+ CamelCaseUtils.toCamelCase(tableName));
            printJPA(tableName, columnList);
        }

    }

    public void printJPA(String tableName, List<TbColumn> columnList){

        String className = CamelCaseUtils.toCamelCase("_"+tableName);
        System.out.println("@Data");
        System.out.println("@Entity");
        System.out.println("@Table(name = \"" + tableName + "\")");
        System.out.println("public class " + className + " implements Serializable {");
        System.out.println("    \n");

        for (TbColumn t : columnList) {
            String columnName = CamelCaseUtils.toCamelCase(t.getColumnName());
            if ("id".equalsIgnoreCase(columnName)){
                System.out.println("    @Id");
                System.out.println("    @SequenceGenerator( name = \""+tableName.toLowerCase()+"_id\", sequenceName = \""+tableName+"_SEQ\" ,allocationSize = 1)");
                System.out.println("    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = \""+tableName.toLowerCase()+"_id\" )");
                System.out.println("    private Long id;");
                System.out.println("    \n");
                continue;
            }
            System.out.println("    @Column(name = \"" + t.getColumnName().toUpperCase() + "\")");
            System.out.println("    private " + sqlTypeMap.get(t.getDataType()) + " " + columnName + ";");
            System.out.println("    \n");
        }
        System.out.println("}");
    }
}
