package com.koujiang.platform.core.exception.annotation;

import java.lang.annotation.*;

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
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AnswerStatus {

    AnswerCode value() default AnswerCode.FAIL;

    String reason() default "";
}
