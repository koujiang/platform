package com.koujiang.platform.core.exception.annotation;

import lombok.Getter;

/**
 * <br>(c) Copyright koujiang901123@sima.com
 * <br>@description	:
 * <br>@file_name	:null.java
 * <br>@system_name	:platform
 * <br>@author		:Administrator
 * <br>@create_time	:2019/5/6 16:22
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
@Getter
public enum AnswerCode {

    REMOTE_LOGIN(601, "异地登录"),
    VERSION_UPDATE(600, "版本更新"),
    TOKEN_FAILURE(511, "token失效"),
    SUCCESS(200, "请求成功"),
    FAIL(500, "请求异常");

    private int code;
    private String reason;

    AnswerCode(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }
}
