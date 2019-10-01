package com.unknown.common.convert;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * <br>(c) Copyright koujiang901123@sina.com
 * <br>@description	:对象转换（Object to Map && Map to Object）
 * <br>@system_name	:platform
 * <br>@author		:koujiang
 * <br>@create_time	:2019/9/5 13:05
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 */
public class ObjectConvert {

    public static <T extends Object> Map<String, Object> convert(T o) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(o, Map.class);
    }

    public static <T extends Object> T convert(Map<String, Object> map, Class<T> clas) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(map, clas);
    }
}
