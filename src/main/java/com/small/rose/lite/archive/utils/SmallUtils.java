package com.small.rose.lite.archive.utils;

import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * @Project: small-lite-archive
 * @Author: 张小菜
 * @Description: [ SmallUtils ] 说明： 无
 * @Function: 功能描述： 无
 * @Date: 2023/11/19 019 13:59
 * @Version: v1.0
 */
public class SmallUtils {

    public static boolean isNotEmpty(@Nullable Collection<?> collection){
        return !isEmpty(collection);
    }

    public static boolean isEmpty(@Nullable Collection<?> collection){
        return org.springframework.util.CollectionUtils.isEmpty(collection);
    }


    public static boolean isNotEmpty(@Nullable Object object){
        return !isEmpty(object);
    }

    public static boolean isEmpty(@Nullable Object object) {
        return org.springframework.util.ObjectUtils.isEmpty(object);
    }

    public static boolean hasText(@Nullable String str){
        return org.springframework.util.StringUtils.hasText(str);
    }


    public static int countSubstring(String text, String sub) {
        return text.split(sub, -1).length - 1;
    }


}
