package com.koujiang.platform.data.jdbc;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <br>(c) Copyright 2017 koujiang901123@sina.com
 * <br>@description	:数据库返回结果处理
 * <br>@file_name	:ResultHelp.java
 * <br>@author		:koujiang 
 * <br>@create_time	:2017年3月20日 下午5:29:05
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
public class ResultHelp {

	public static final <E> E transToBean(ResultSet rs, Class<E> clas) throws Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		//实例化一个解析工具
		if (rs.next()) {
			E e = clas.newInstance();
            Map<String, AttributeCache> collect = Analytic.getAttributes(clas).stream().collect(Collectors.toMap(o -> o.getDbname(), o -> o));
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                AttributeCache cache = collect.get(rsmd.getColumnName(i));
				if(cache != null)
					setBeanFieldValueFromRS(e, cache, rs);
			}
			return e;
		}
		return null;
	}
	
	public static final <E> List<E> transToList(ResultSet rs, Class<E> clas) throws Exception {
		List<E> list = new ArrayList<E>();
		ResultSetMetaData rsmd = rs.getMetaData();
		//将结果集合封装成 baseDto
        AttributeCache[] caches = new AttributeCache[rsmd.getColumnCount()];
		boolean isSetField = false;
        Map<String, AttributeCache> collect = Analytic.getAttributes(clas).stream().collect(Collectors.toMap(o -> o.getDbname(), o -> o));
        while (rs.next()) {
			E e = clas.newInstance();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				if (!isSetField) {
                    caches[i - 1] = collect.get(rsmd.getColumnName(i));
				}
                AttributeCache cache = caches[i - 1];
				if(cache != null) {
					setBeanFieldValueFromRS(e, cache, rs);
				}
			}
			list.add(e);
			if (!isSetField) isSetField = true;
		}
		return list.isEmpty() ? null : list;
	}
	
	private static final <E> void setBeanFieldValueFromRS(E e, AttributeCache cache, ResultSet rs) throws Exception {
        Field field = cache.getField();
        String name = cache.getName();

        if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
			try {
				field.set(e, rs.getInt(name));
			} catch (Exception e1) {
				field.set(e, null);
			}
		} else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
			try {
				field.set(e, rs.getLong(name));
			} catch (Exception e1) {
				field.set(e, null);
			}
		} else if (field.getType().equals(double.class) || field.getType().equals(Double.class)
				|| field.getType().equals(float.class) || field.getType().equals(Float.class)) {
			try {
				field.set(e, rs.getDouble(name));
			} catch (Exception e1) {
				field.set(e, null);
			}
		} else {
			String obj = rs.getString(name);
			field.set(e, obj==null ? "" : obj);
		}
	}
	
}
