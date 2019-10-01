package com.unknown.data.jdbc;

import java.util.List;
import java.util.Map;

/**
 * <br>(c) Copyright 2017 koujiang901123@sina.com
 * <br>@description	:Data Access Operate
 * <br>@file_name	:SQLOperate.java
 * <br>@author		:koujiang 
 * <br>@create_time	:2017年11月16日 上午10:13:49
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
public interface ISQLOperate {
	
	/*------------------------------------persistence---------------------------------*/
	
	/**
	 * description executeSQL:持久化操作(增、删、改)
	 * <br>author ：koujiang
	 * <br>@param sql
	 * <br>@param params
	 * <br>@return 返回受影响的行数
	 * <br>@throws RuntimeException 
	 * <br>return :int
	 * <br>update ：2017年3月10日 下午5:15:54
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	@Deprecated
	public int executeSQL(String sql, Object[] params) throws RuntimeException;
	
	/**
	 * description findSQL:持久化操作(查询)
	 * [返回格式以查询语句的列为数组,多行就返回List]
	 * <br>author ：koujiang
	 * <br>@param sql
	 * <br>@param params
	 * <br>@return
	 * <br>@throws RuntimeException 
	 * <br>return :List<Object[]>
	 * <br>update ：2017年3月10日 下午6:25:01
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public List<Object[]> querySQLForArray(String sql, Object[] params) throws RuntimeException;
	
	/**
	 * description querySQLForMap:持久化操作(查询)
	 * [返回格式以查询语句的列名为键一行数据就为Map,多行就返回List]
	 * <br>author ：koujiang
	 * <br>@param sql
	 * <br>@param params
	 * <br>@return
	 * <br>@throws RuntimeException 
	 * <br>return :List<Map<String,Object>>
	 * <br>update ：2017年3月10日 下午6:27:31
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public List<Map<String, Object>> querySQLForMap(String sql, Object[] params) throws RuntimeException;
	
	/*--------------------------------------insert-----------------------------------*/
	
	/**
	 * description insertObject:添加对象
	 * <br>author ：koujiang
	 * <br>@param e
	 * <br>@return
	 * <br>@throws RuntimeException 
	 * <br>return :E
	 * <br>update ：2017年3月10日 下午4:54:25
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	@Deprecated
	public <E> E insertObject(E e) throws RuntimeException;
	
	/**
	 * description insertObject:添加对象
	 * <br>author ：koujiang
	 * <br>@param es
	 * <br>@return
	 * <br>@throws RuntimeException 
	 * <br>return :List<E>
	 * <br>update ：2017年2月15日 下午3:25:58
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	@Deprecated
	public <E> List<E> insertObject(List<E> es) throws RuntimeException;
	
	/*--------------------------------------update-----------------------------------*/
	/**
	 * description updateObject:更新
	 * <br>author ：koujiang
	 * <br>@param e
	 * <br>@param fields		指定需要更新的列表(属性名称),如果未指定就全部更新
	 * <br>@return
	 * <br>@throws RuntimeException 
	 * <br>return :E
	 * <br>update ：2017年2月15日 下午3:21:20
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	@Deprecated
	public <E> int updateObject(E e, String... fields) throws RuntimeException;
	
	/**
	 * description updateObject:更新
	 * <br>author ：koujiang
	 * <br>@param es
	 * <br>@param fields		指定需要更新的列表(属性名称),如果未指定就全部更新
	 * <br>@return
	 * <br>@throws RuntimeException 
	 * <br>return :List<E>
	 * <br>update ：2017年2月15日 下午3:26:08
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	@Deprecated
	public <E> int updateObject(List<E> es, String... fields) throws RuntimeException;
	
	/**
	 * description updateObjectNep:更新对象中非空属性字段
	 * <br> nep Not empty properties
	 * <br>author ：koujiang
	 * <br>@param e
	 * <br>@return
	 * <br>@throws RuntimeException 
	 * <br>return :E
	 * <br>update ：2017年3月10日 下午5:06:59
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	@Deprecated
	public <E> int updateObjectNep(E e) throws RuntimeException;
	
	/**
	 * description updateObjectNep:更新对象中非空属性字段
	 * <br>author ：koujiang
	 * <br>@param es
	 * <br>@return
	 * <br>@throws RuntimeException 
	 * <br>return :List<E>
	 * <br>update ：2017年3月10日 下午5:11:44
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	@Deprecated
	public <E> int updateObjectNep(List<E> es) throws RuntimeException;
	
	/*--------------------------------------del-----------------------------------*/
	
	/**
	 * description deleteObject:删除
	 * <br>author ：koujiang
	 * <br>@param e
	 * <br>@return
	 * <br>@throws RuntimeException 
	 * <br>return :int
	 * <br>update ：2017年2月15日 下午3:30:09
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	@Deprecated
	public <E> int deleteObject(E e) throws RuntimeException;
	
	/**
	 * description delateObject:删除
	 * <br>author ：koujiang
	 * <br>@param es
	 * <br>@return
	 * <br>@throws RuntimeException 
	 * <br>return :int
	 * <br>update ：2017年2月15日 下午3:30:16
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	@Deprecated
	public <E> int delateObject(List<E> es) throws RuntimeException;
	
	/*--------------------------------------query-----------------------------------*/
	
	/**
	 * description queryForString:依据SQL查询并返回一个字符串(实用与查询单个字段行)
	 * <br>author ：koujiang
	 * <br>@param sql
	 * <br>@param params
	 * <br>@return
	 * <br>@throws RuntimeException 
	 * <br>return :String
	 * <br>update ：2017年2月15日 下午3:02:18
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public String queryForString(String sql, Object... params) throws RuntimeException;
	
	/**
	 * description queryForBean:查询并返回一个对象
	 * <br>author ：koujiang
	 * <br>@param sql
	 * <br>@param params
	 * <br>@param cls
	 * <br>@return
	 * <br>@throws RuntimeException 
	 * <br>return :E
	 * <br>update ：2017年2月15日 下午3:08:28
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public <E> E queryForBean(String sql, Object[] params, Class<E> cls) throws RuntimeException;
	
	/**
	 * description queryForList:查询并返回List
	 * <br>author ：koujiang
	 * <br>@param sql
	 * <br>@param params
	 * <br>@param cls
	 * <br>@return
	 * <br>@throws RuntimeException 
	 * <br>return :List<E>
	 * <br>update ：2017年2月15日 下午3:09:16
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public <E> List<E> queryForList(String sql, Object[] params, Class<E> cls) throws RuntimeException;

	/*-----------------------------------分页查询-----------------------------------*/
}
