package com.unknown.core.exception;

/**
 * <br>(c) Copyright koujiang901123@sima.com
 * <br>@description	:操作数据或库出现异常
 * <br>@file_name	:null.java
 * <br>@system_name	:platform
 * <br>@author		:Administrator
 * <br>@create_time	:2018/12/27 15:56
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
public class DataException extends RuntimeException {
    public DataException(String message) {
        super(message);
    }
}
