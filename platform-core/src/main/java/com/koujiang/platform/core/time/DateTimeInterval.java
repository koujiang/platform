package com.koujiang.platform.core.time;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <br>(c) Copyright koujiang901123@sima.com
 * <br>@description	:时间区间工具类
 * <br>@file_name	:null.java
 * <br>@system_name	:online
 * <br>@author		:Administrator
 * <br>@create_time	:2019/5/28 18:07
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateTimeInterval {

    LocalDateTime start;
    LocalDateTime end;

    //验证时间是否在此区间
    public boolean isInterval(LocalDateTime dateTime) {
        //one.isAfter(tow) //前一个时间在后一个时间之后
        //one.isBefore(tow)//前一个时间在后一个时间之前
        if (!dateTime.isBefore(start) && !dateTime.isAfter(end)) {
            return true;
        } else {
            return false;
        }
    }

    //传入时间是否在时间段结束时间之后
    public boolean isAfter(LocalDateTime dateTime) {
        return dateTime.isAfter(end);
    }

    //传入时间是否在时间段开始时间之前
    public boolean isBefore(LocalDateTime dateTime) {
        return dateTime.isBefore(start);
    }

    public DateTimeInterval delay(Long delayStart) {
        return new DateTimeInterval(start.plusMinutes(delayStart), end);
    }

    public DateTimeInterval advance(Long advanceStop) {
        return new DateTimeInterval(start, end.minusMinutes(advanceStop));
    }

    public DateTimeInterval adjust(Long delayStart,Long advanceStop) {
        return new DateTimeInterval(start.plusMinutes(delayStart), end.minusMinutes(advanceStop));
    }
}