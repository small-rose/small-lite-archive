package com.small.rose.lite.archive.emuns;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ ArchiveJobStatusEnum ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 16:32
 * @Version: v1.0
 */
public enum ArchiveJobStatusEnum {

    PREPARE("PREPARE", "归档任务配置准备完成"),
    MIGRATING("MIGRATING", "归档作业任务数搬运中"),
    MIGRATED_SUCCESS("MIGRATED", "归档作业任务数搬运完成"),
    MIGRATED_FAILED("MIGRATED_FAILED", "归档作业任务数搬运完成"),
    VERIFYING("VERIFYING", "归档作业任务数据校对中"),
    VERIFY_SUCCESS("VERIFY_SUCCESS", "归档作业任务数据校对完成"),
    VERIFY_FAILED("VERIFY_FAILED", "归档作业任务数据校对完成"),
    DELETE("DELETE", "归档作业任务源表数据删除中"),
    DELETE_FAILED("DELETE_FAILED", "归档作业任务源表数据删除中"),
    SUCCESS("SUCCESS", "归档作业任务全部完成"),
    ERROR_FAILED("ERROR_FAILED", "归档作业任务出错");
    private String status;
    private String desc;

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    ArchiveJobStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static ArchiveJobStatusEnum getParamType(String confMode) {
        for (ArchiveJobStatusEnum modeType : ArchiveJobStatusEnum.values()) {
            if (modeType.status.equals(confMode)) {
                return modeType;
            }
        }
        return null;
    }
}
