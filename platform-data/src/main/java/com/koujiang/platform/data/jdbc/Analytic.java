package com.koujiang.platform.data.jdbc;

import com.unknown.platform.util.VNETookit;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <br>(c) Copyright 2017 koujiang901123@sina.com
 * <br>@description	:解析注解类
 * <br>@file_name	:Analytic.java
 * <br>@author		:koujiang 
 * <br>@create_time	:2017年11月16日 上午10:17:59
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
public class Analytic {

	//className , PK
	private static Map<String, Field> primaryKeyFieldCache = new HashMap<>();
	private static Map<String, String> primaryKeyNameCache = new HashMap<>();
	//className , table
	private static Map<String, String> tableCache = new HashMap<>();
	//className , attrbute
	private static Map<String, List<AttributeCache>> attributes = new HashMap<>();

	public static String getTabName(Object e) {
		Class<?> clas = e.getClass();
		return getTabName(clas);
	}

	public static String getTabName(Class clas) {
		if (!isCache(clas)) {
			cache(clas);
		}

		if (tableCache.containsKey(clas.getName()))
			return tableCache.get(clas.getName());
		return null;
	}

	private static Field getPk(Class clas) {
		if (!isCache(clas)) {
			cache(clas);
		}

		if (primaryKeyFieldCache.containsKey(clas.getName()))
			return primaryKeyFieldCache.get(clas.getName());
		return null;
	}

	public static List<AttributeCache> getAttributes(Class clas) {
		boolean cache = isCache(clas);
		if (!cache) {
			cache(clas);
		}

		if (attributes.containsKey(clas.getName()))
			return attributes.get(clas.getName());
		return null;
	}

	public static List<AttributeCache> getAttributes(Object e, String... colNames) {
		List<AttributeCache> attributes = getAttributes(e.getClass());
		if (colNames != null && colNames.length > 0) {
			List<String> colList = Arrays.asList(colNames);
			return attributes.stream().filter(o -> colList.contains(o.getName())).collect(Collectors.toList());
		} else {
			return attributes;
		}
	}

	public static Object getPkValue(Object e) {
		try {
			Class<?> clas = e.getClass();
			Field field = getPk(clas);
			if (field == null)
				return null;

			return field.get(e);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Object getPkDbName(Object e) {
		if (isCache(e.getClass()))
			cache(e.getClass());

		return primaryKeyNameCache.containsKey(e.getClass().getName()) ? primaryKeyNameCache.get(e.getClass().getName()) : null;
	}

	private static synchronized <E> boolean isCache(Class<E> clas) {
		if (attributes.containsKey(clas.getName()))
			return true;
		return false;
	}

	private static synchronized <E> void cache(Class<E> clas) {
		if (isCache(clas)) {
			return;
		}
		String className = clas.getName();
		//获取表名
		String tableName = findTableName(clas);
		if (VNETookit.isNull(tableName))
			return;
		tableCache.put(className, tableName);
		//获取所有属性
		List<AttributeCache> attribute = findAttribute(clas);
		if (attribute != null && !attribute.isEmpty()) {
			attributes.put(className, attribute);
		}
	}

	private static List<AttributeCache> findAttribute(Class clas) {
		List<AttributeCache> attributes = new ArrayList<>();
		if (clas.isInstance(Collection.class)) {

		} else if (clas.isInstance(Map.class)) {

		} else {
			Field[] fields = clas.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);

				String name = field.getName();
				String dbname = findDbName(field);

				Embedded annotation = field.getAnnotation(Embedded.class);
				if (!VNETookit.isNull(annotation)) {
					List<AttributeCache> attribute = findAttribute(field.getType());


				}

				Class<?> type = field.getType();

				AttributeCache cache = new AttributeCache(name, humpToLine(dbname), field);
			}
		}
		return attributes;
	}

	private static Field findPk(Class clas) {
		if (isCache(clas))
			cache(clas);
		return primaryKeyFieldCache.get(clas.getName());
	}

	private static String findDbName(Field field) {
		Column column = field.getAnnotation(Column.class);
		if (!VNETookit.isNull(column) && !VNETookit.isNull(column.name()))
			return column.name();
		JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
		if (!VNETookit.isNull(joinColumn) && !VNETookit.isNull(joinColumn.name()))
			return joinColumn.name();
		String name = field.getName();
		return name.substring(name.lastIndexOf("."));
	}

	private static String findTableName(Class clas) {
		try {
			Annotation annotation = clas.getAnnotation(Entity.class);
			if (annotation == null)
				return null;
			Entity entity = (Entity)annotation;
			if (!VNETookit.isNull(entity) && !VNETookit.isNull(entity.name())) {
				return humpToLine(entity.name());
			} else {
				return clas.getName().substring(clas.getName().lastIndexOf("."));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return humpToLine(clas.getName().substring(clas.getName().lastIndexOf(".")));
	}

	//下划线转驼峰
	private static Pattern linePattern = Pattern.compile("_(\\w)");
	private static String lineToHump(String str){
		str = str.toLowerCase();
		Matcher matcher = linePattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while(matcher.find()){
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	//驼峰转下划线
	private static Pattern humpPattern = Pattern.compile("[A-Z]");
	private static String humpToLine(String str){
		Matcher matcher = humpPattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while(matcher.find()){
			matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
}