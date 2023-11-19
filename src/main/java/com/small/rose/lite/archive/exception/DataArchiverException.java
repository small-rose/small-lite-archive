package com.small.rose.lite.archive.exception;

/**
 * @Project : small-db-archive
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2023/11/12 012 0:22
 * @version: v1.0
 */
public class DataArchiverException extends RuntimeException{


    public DataArchiverException() {
        super();
    }


    public DataArchiverException(String message) {
        super(message);
    }


    public DataArchiverException(String message, Throwable cause) {
        super(message, cause);
    }
}
