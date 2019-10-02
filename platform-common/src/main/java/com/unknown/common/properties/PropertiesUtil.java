package com.unknown.common.properties;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <br>(c) Copyright koujiang901123@sina.com
 * <br>@description	:上下文配置文件属性读取
 * <br>@file_name	:PropertiUtil.java
 * <br>@system_name	:com.unknown.platform.common.basic.PropertiUtil
 * <br>@author		:koujiang
 * <br>@create_time	:2019/8/29 2:56
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 */
public class PropertiesUtil {

    private static Map<String, String> propertyMap = new LinkedHashMap<>();

    private Properties properties;

    public static void load(String propertiName,Object obj) {
        new PropertiesUtil().load(propertiName,obj.getClass());
    }

    /**
    * <br>description :加载配置文件
    * <br>@author ： koujiang
    * <br>@param  ： [propertiName, clazz]
    * <br>@return ： void
    * <br>@update ： 2019/8/30 0:55
    * <br>@mender ：(Please add the modifier name)
    * <br>@Modified ：(Please add modification date)
    */
    public <T> void load(String propertiName,Class<T> clazz) {
        InputStream in = clazz.getClassLoader().getResourceAsStream(propertiName);
        properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {

            //log.("配置文件加载失败！！！param[propertiName:{},class:{}]",propertiName,clazz);
            e.printStackTrace();
        }
        for (Object keyObj : properties.keySet()) {
            String key = keyObj.toString();
            propertyMap.put(key,properties.getProperty(key));
        }
    }

    /**
    * <br>description :获取配置文件数据
    * <br>@author ： koujiang
    * <br>@param  ： [name]
    * <br>@return ： java.lang.String
    * <br>@update ： 2019/8/30 0:56
    * <br>@mender ：(Please add the modifier name)
    * <br>@Modified ：(Please add modification date)
    */
    public static String getProperty(String name) {
        return propertyMap.get(name);
    }
}
