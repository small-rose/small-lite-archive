package com.small.rose.lite.archive.emuns;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ ArchiveParamTypeEnum ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 17:52
 * @Version: v1.0
 */
public enum  ArchiveParamTypeEnum {

    NUMBER("Long"),
    VARCHAR("String"),
    DATE("DATE");

    private String type ;

    ArchiveParamTypeEnum(String type) {
        this.type = type;
    }

    public static ArchiveParamTypeEnum getParamType(String confMode) {
        for (ArchiveParamTypeEnum modeType : ArchiveParamTypeEnum.values()){
            if (modeType.type.equals(confMode)){
                return modeType;
            }
        }
        return null;
    }
}
