package com.koujiang.platform.data.jdbc;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

/**
 * <br>(c) Copyright koujiang901123@sima.com
 * <br>@description	:
 * <br>@file_name	:null.java
 * <br>@system_name	:platform
 * <br>@author		:Administrator
 * <br>@create_time	:2019/3/13 22:12
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
@Getter
@Setter
public class AttributeCache {

    private Boolean primaryKey = Boolean.FALSE;
    private String name;
    private String dbname;
    private Field field;
    private Field[] fields;

    public AttributeCache(String name, String dbname, Field field) {
        this.name = name;
        this.dbname = dbname;
        this.field = field;
    }

    public AttributeCache(String name, String dbname, Field... fields) {
        this.name = name;
        this.dbname = dbname;
        this.fields = fields;
    }

    public AttributeCache(Boolean primaryKey, String name, String dbname, Field field) {
        this(name, dbname, field);
        this.primaryKey = primaryKey;
    }

    public Object getValue(Object e) throws IllegalArgumentException, IllegalAccessException {
        return field.get(e);
    }

    public boolean isPk() {
        return primaryKey;
    }

}
