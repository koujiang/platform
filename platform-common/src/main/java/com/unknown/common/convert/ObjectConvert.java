package com.unknown.common.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unknown.core.exception.ModelException;

import java.io.IOException;
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

    public static <T extends Object> Map<String, Object> toMap(T o) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(o, Map.class);
    }

    public static <T extends Object> String toString(T o) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new ModelException(e.getMessage());
        }
    }

    public static <T extends Object> T toObject(Map<String, Object> map, Class<T> clas) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(map, clas);
    }

    public static <T extends Object> T toObject(String str, Class<T> clas) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(str, clas);
        } catch (IOException e) {
            throw new ModelException(e.getMessage());
        }
    }
}
