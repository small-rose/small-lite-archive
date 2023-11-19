package com.small.rose.lite.archive.emuns;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ ArchiveStrategyEnum ] 枚举类
 * @Function: 枚举功能描述： 无
 * @Date: 2023/11/19 019 14:49
 * @Version: v1.0
 */
public enum ArchiveStrategyEnum {

    ARCHIVE_PK_NUMBER("ARCHIVE_PK_NUMBER"),
    ARCHIVE_PK_STRING("ARCHIVE_PK_STRING"),
    ARCHIVE_DATE_PAGE("ARCHIVE_DATE_PAGE"),
    NULL_MODE("NULL_MODE");

    private String mode ;

    ArchiveStrategyEnum(String mode) {
        this.mode = mode;
    }

    public static ArchiveStrategyEnum getStrategy(String confMode) {
        for (ArchiveStrategyEnum modeType : ArchiveStrategyEnum.values()){
            if (modeType.mode.equals(confMode)){
                return modeType;
            }
        }
        return NULL_MODE;
    }

    public static String getAllModeName() {
        StringBuffer sb = new StringBuffer("[");
        for (ArchiveStrategyEnum modeType : ArchiveStrategyEnum.values()){
            if (!modeType.name().equals(NULL_MODE.name())){
                sb.append(modeType.name()).append(",");
            }
        }
        String result = sb.substring(0, sb.length()-1);
        result = result.concat("]");
        return result;
    }


}
