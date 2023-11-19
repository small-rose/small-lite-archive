package com.small.rose.lite.archive.core.strategys.check;

import com.small.rose.lite.archive.emuns.ArchiveStrategyEnum;
import com.small.rose.lite.archive.exception.ArchiveCheckException;
import com.small.rose.lite.archive.module.pojo.AmsArchiveJobConfig;
import com.small.rose.lite.archive.module.service.jdbc.ArchiveSqlService;
import com.small.rose.lite.archive.utils.SmallDateUtils;
import com.small.rose.lite.archive.utils.SmallUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Project: small-db-archive
 * @Author: 张小菜
 * @Description: [ PkNumModeCheckStrategy ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/13 013 23:38
 * @Version: v1.0
 */

@Slf4j
@Component
public class ArchiveByDateCheckStrategy implements ArchiveJobModeCheck {

    @Autowired
    private ArchiveSqlService archiveSqlService;


    @Override
    public ArchiveStrategyEnum getArchiveStrategy() {
        return ArchiveStrategyEnum.ARCHIVE_DATE_PAGE;
    }

    @Override
    public boolean check(AmsArchiveJobConfig jobConfig) {

        if (!SmallUtils.hasText(jobConfig.getJobDeadLine())){
            log.info("归档使用[{}]策略必须配置[截止日期列]不允许为空。", getArchiveStrategy());
            throw new ArchiveCheckException("归档使用[" + getArchiveStrategy() + "]策略必须配置主键列不允许为空。");
        }

        // 查到了待归档的最小日期
        Date minDate = archiveSqlService.queryMinDateForColumn(jobConfig);
        Date dateLine = archiveSqlService.queryDateLineDate(jobConfig);
        if(SmallDateUtils.compareDate(minDate, dateLine) >= 0){
            log.info("本次无可归档数据，不生成归档任务！");
            return false;
        }
        return true;
    }
}
