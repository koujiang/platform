package com.koujiang.platform.core.exception;

/**
 * <br>(c) Copyright koujiang901123@sima.com
 * <br>@description	:Model 模块的异常
 * <br>@file_name	:null.java
 * <br>@system_name	:platform
 * <br>@author		:Administrator
 * <br>@create_time	:2018/12/27 15:44
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
public class ModelException extends RuntimeException {
    public ModelException(String message) {
        super(message);
    }
}
