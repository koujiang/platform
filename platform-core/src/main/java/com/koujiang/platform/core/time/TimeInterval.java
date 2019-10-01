package com.koujiang.platform.core.time;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
public class TimeInterval {

    LocalTime start;
    LocalTime end;

    public DateTimeInterval toInterval(LocalDate date) {
        LocalDateTime startTime = date.atTime(this.start);
        LocalDateTime endTime = date.atTime(this.end);
        if (!startTime.isBefore(endTime)) {
            endTime = endTime.plusDays(1);
        }
        return new DateTimeInterval(startTime, endTime);
    }

    public TimeInterval delay(Long delayStart) {
        return new TimeInterval(start.plusMinutes(delayStart), end);
    }

    public TimeInterval advance(Long advanceStop) {
        return new TimeInterval(start, end.minusMinutes(advanceStop));
    }

    public TimeInterval adjust(Long delayStart,Long advanceStop) {
        return new TimeInterval(start.plusMinutes(delayStart), end.minusMinutes(advanceStop));
    }

}
