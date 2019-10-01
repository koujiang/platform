package com.unknown.data.jdbc;

import com.unknown.core.util.VNETookit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <br>(c) Copyright 2017 koujiang901123@sina.com
 * <br>@description	:SQL拼装工具
 * <br>@file_name	:SQLHelper.java
 * <br>@author		:koujiang 
 * <br>@create_time	:2017年3月13日 上午11:46:10
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
public class SQLHelper {

	private static SQLHelper sh = new SQLHelper();
	
	public class SQLValueHandler {
		public String sql;
		public Object[] values;
	}
	
	public class BatchSQLValueHandler {
		public String sql;
		public Object[][] values;
	}
	
	/**
	 * description insertSQL:根据bean生成标准SQL 插入语句，valus用占位符
	 * <br>author ：koujiang
	 * <br>@param e
	 * <br>@return
	 * <br>@throws IllegalArgumentException
	 * <br>@throws IllegalAccessException 
	 * <br>return :SQLValueHandler
	 * <br>update ：2017年3月13日 下午2:05:10
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public static final <E> SQLValueHandler insertSQL(E e) throws IllegalArgumentException, IllegalAccessException {
		if (VNETookit.isNull(e)) return null;
		SQLValueHandler svh = sh.new SQLValueHandler();
		StringBuffer sql_field = new StringBuffer("insert into ");
		sql_field.append(Analytic.getTabName(e)).append("(");
		
		StringBuffer sql_values = new StringBuffer(" values(");

		List<AttributeCache> caches = Analytic.getAttributes(e);
		Object[] objArr = new Object[caches.size()];

		Iterator<AttributeCache> it = caches.iterator();
		int i = 0;
		while (it.hasNext()) {
			AttributeCache next = it.next();
			/**
			 * 主键生成规则是由数据库自动增长情况下，生成插入语句时，跳过主键字段
			 */
			/*if (DBConfig.getPKGM().equals(PKGenerateType.AUTO_INCREMENT.get())
					&& entry.getKey().equals(analy.getPKDBName())) {
				continue;
			}*/
			sql_field.append(next.getDbname()).append(", ");
			sql_values.append("?, ");
			objArr[i] = next.getValue(e);
			i ++;
		}
		sql_field.delete(sql_field.length() - 2, sql_field.length()).append(")");
		sql_values.delete(sql_values.length() - 2, sql_values.length()).append(")");
		svh.sql = sql_field.append(sql_values).toString();
		svh.values = objArr;
		return svh;
	}

	/**
	 * description insertSQL:根据bean生成标准SQL 插入语句，valus用占位符
	 * <br>author ：koujiang
	 * <br>@param es
	 * <br>@return
	 * <br>@throws IllegalArgumentException
	 * <br>@throws IllegalAccessException 
	 * <br>return :SQLValueHandler
	 * <br>update ：2017年3月13日 下午2:33:19
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public  static final <E> BatchSQLValueHandler insertSQL(List<E> es) throws IllegalArgumentException, IllegalAccessException {
		if (VNETookit.isNull(es)) return null;
		E e = es.get(0);
		BatchSQLValueHandler svh = sh.new BatchSQLValueHandler();
		
		StringBuffer sql_field = new StringBuffer("insert into ");
		sql_field.append(Analytic.getTabName(e)).append("(");
		StringBuffer sql_values = new StringBuffer(" values(");
		List<AttributeCache> caches = Analytic.getAttributes(e);
		Object[][] objArr = new Object[es.size()][caches.size()];
		Iterator<AttributeCache> it = caches.iterator();
		int i = 0;
		while (it.hasNext()) {
			AttributeCache next = it.next();
			/**
			 * 主键生成规则是由数据库自动增长情况下，生成插入语句时，跳过主键字段
			 */
			/*if (DBConfig.getPKGM().equals(PKGenerateType.AUTO_INCREMENT.get())
					&& entry.getKey().equals(analy.getPKDBName())) {
				continue;
			}*/
			sql_field.append(next.getDbname()).append(", ");
			sql_values.append("?, ");
			for (int j = 0; j < es.size(); j++) {
				objArr[j][i] = next.getValue(es.get(j));
			}
			i ++;
		}
		sql_field.delete(sql_field.length() - 2, sql_field.length()).append(")");
		sql_values.delete(sql_values.length() - 2, sql_values.length()).append(")");
		svh.sql = sql_field.append(sql_values).toString();
		svh.values = objArr;
		return svh;
	}

	/**
	 * description updateSQL:根据bean生成标准SQL 更新语句
	 * <br>author ：koujiang
	 * <br>@param e
	 * <br>@param fields		field.getName()
	 * <br>@return
	 * <br>@throws IllegalArgumentException
	 * <br>@throws IllegalAccessException 
	 * <br>return :SQLValueHandler
	 * <br>update ：2017年3月13日 下午3:10:28
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public  static final <E> SQLValueHandler updateSQL(E e, String... fields) throws IllegalArgumentException, IllegalAccessException {
		if (VNETookit.isNull(e)) return null;
		SQLValueHandler svh = sh.new SQLValueHandler();
		StringBuffer sql = new StringBuffer("update ");
		sql.append(Analytic.getTabName(e)).append(" set ");
		if (VNETookit.isNull(fields)) {
			//生成全部字段的更新
			List<AttributeCache> attributes = Analytic.getAttributes(e);
			Object[] values = new Object[attributes.size()];
			Iterator<AttributeCache> it = attributes.iterator();
			int index = 0;
			while (it.hasNext()) {
				AttributeCache next = it.next();
				if (next.isPk()) {
					continue;
				}
				sql.append(next.getDbname()).append(" = ?, ");
				values[index] = next.getValue(e);
				index ++;
			}
			sql.delete(sql.length() - 2, sql.length());
			sql.append(" where ").append(Analytic.getPkDbName(e)).append(" = ?");
			values[values.length - 1] = Analytic.getPkValue(e);
			svh.values = values;
		} else {
			//生成指定字段的更新
			Object[] values = new Object[fields.length + 1];
			List<AttributeCache> caches = Analytic.getAttributes(e, fields);
			for (int i = 0; i < caches.size(); i++) {
				AttributeCache cache = caches.get(i);
				if (cache.isPk()) throw new IllegalArgumentException("主键不能修改");
				sql.append(cache.getDbname()).append(" = ?, ");
				values[i] = cache.getValue(e);
			}
			sql.delete(sql.length() - 2, sql.length());
			sql.append(" where ").append(Analytic.getPkDbName(e)).append(" = ?");
			values[values.length - 1] = Analytic.getPkValue(e);
			svh.values = values;
		}
		svh.sql = sql.toString();
		return svh;
	}
	
	/**
	 * description updateSQL:根据bean生成标准SQL 更新语句
	 * <br>author ：koujiang
	 * <br>@param es
	 * <br>@param fields		field.getName()
	 * <br>@return
	 * <br>@throws IllegalArgumentException
	 * <br>@throws IllegalAccessException 
	 * <br>return :BatchSQLValueHandler
	 * <br>update ：2017年3月13日 下午3:40:01
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public  static final <E> BatchSQLValueHandler updateSQL(List<E> es, String... fields) throws IllegalArgumentException, IllegalAccessException {
		if (VNETookit.isNull(es)) return null;
		E e = es.get(0);
		BatchSQLValueHandler bsvh = sh.new BatchSQLValueHandler();
		StringBuffer sql = new StringBuffer("update ");
		sql.append(Analytic.getTabName(e)).append(" set ");
		boolean tmp = false;
		
		if (VNETookit.isNull(fields)) {
			//生成全部字段的更新
			//生成全部字段的更新
			List<AttributeCache> attributes = Analytic.getAttributes(e);
			Iterator<AttributeCache> it = attributes.iterator();
			Object[][] values = new Object[es.size()][attributes.size()];
			int i = 0;
			while (it.hasNext()) {
				AttributeCache next = it.next();
				if (next.isPk())
					continue;

				sql.append(next.getDbname()).append(" = ?, ");
				for (int j = 0; j < es.size(); j++) {
					values[j][i] = next.getValue(es.get(j));
					//设置修改条件
					if (!tmp) {
						values[j][values[j].length - 1] = Analytic.getPkValue(es.get(j));
					}
				}
				if (!tmp) tmp = true;
				i ++;
			}
			sql.delete(sql.length() - 2, sql.length());
			sql.append(" where ").append(Analytic.getPkDbName(e)).append(" = ?");
			bsvh.values = values;
		} else {
			//生成指定字段的更新
			List<AttributeCache> caches = Analytic.getAttributes(e, fields);
			Object[][] values = new Object[es.size()][caches.size() + 1];
			for (int i = 0; i < caches.size(); i++) {
				AttributeCache cache = caches.get(i);
				if (cache.isPk()) throw new IllegalArgumentException("主键不能修改");

				sql.append(cache.getDbname()).append(" = ?, ");
				for (int j = 0; j < es.size(); j++) {
					values[j][i] = cache.getValue(es.get(j));
					if (!tmp) {
						values[j][values[j].length - 1] = Analytic.getPkValue(es.get(j));
					}
				}
				if (!tmp) tmp = true;
			}
			sql.delete(sql.length() - 2, sql.length());
			sql.append(" where ").append(Analytic.getPkDbName(e)).append(" = ?");
			bsvh.values = values;
		}
		bsvh.sql = sql.toString();
		return bsvh;
	}
	
	/**
	 * description updateNepSQL:生成标准SQL 更新语句，生成的更新语句中只包含Bean中不是 null的属性字段
	 * <br>author ：koujiang
	 * <br>@param e
	 * <br>@return
	 * <br>@throws IllegalArgumentException
	 * <br>@throws IllegalAccessException 
	 * <br>return :SQLValueHandler
	 * <br>update ：2017年3月13日 下午3:39:07
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public  static final <E> SQLValueHandler updateNepSQL(E e) throws IllegalArgumentException, IllegalAccessException {
		if (VNETookit.isNull(e)) return null;
		SQLValueHandler svh = sh.new SQLValueHandler();
		StringBuffer sql = new StringBuffer("update ");
		sql.append(Analytic.getTabName(e)).append(" set ");
		List<AttributeCache> caches = Analytic.getAttributes(e);
		Iterator<AttributeCache> it = caches.iterator();
		List<Object> tmpList = new ArrayList<Object>(caches.size());
		while (it.hasNext()) {
			AttributeCache next = it.next();
			if (next.isPk())
				continue;
			Object value = next.getValue(e);
			if (VNETookit.isNull(value)) {
				continue;
			}
			sql.append(next.getDbname()).append(" = ?, ");
			tmpList.add(value);
		}
		sql.delete(sql.length() - 2, sql.length());
		sql.append(" where ").append(Analytic.getPkDbName(e)).append(" = ?");
		tmpList.add(Analytic.getPkValue(e));
		svh.sql = sql.toString();
		svh.values = tmpList.toArray();
		return svh;
	}
	
	/**
	 * description updateNepSQL:生成标准SQL 更新语句，且只生成Bean属性字段不为null的SQL语句，依据的标准是 es 集合的第一个对象
	 * <br>author ：koujiang
	 * <br>@param es
	 * <br>@return
	 * <br>@throws IllegalArgumentException
	 * <br>@throws IllegalAccessException 
	 * <br>return :BatchSQLValueHandler
	 * <br>update ：2017年3月13日 下午3:46:24
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public  static final <E> BatchSQLValueHandler updateNepSQL(List<E> es) throws IllegalArgumentException, IllegalAccessException {
		if (VNETookit.isNull(es)) return null;
		E e = es.get(0);
		BatchSQLValueHandler bsvh = sh.new BatchSQLValueHandler();
		//成标准SQL语句
		StringBuffer sql = new StringBuffer("update ");
		sql.append(Analytic.getTabName(e)).append(" set ");
		List<AttributeCache> tmpFiledList = new ArrayList<>();
		List<AttributeCache> caches = Analytic.getAttributes(e);
		Iterator<AttributeCache> it = caches.iterator();
		while (it.hasNext()) {
			AttributeCache next = it.next();
			if (next.isPk()) {
				continue;
			}
			Object value = next.getValue(e);
			if (VNETookit.isNull(value)) {
				continue;
			}
			tmpFiledList.add(next);
			sql.append(next.getDbname()).append(" = ?, ");
		}
		sql.delete(sql.length() - 2, sql.length());
		sql.append(" where ").append(Analytic.getPkDbName(e)).append(" = ?");
		//生成参数二维数据
		Object[][] values = new Object[es.size()][tmpFiledList.size() + 1];
		for (int i = 0; i < es.size(); i++) {
			E etmp = es.get(i);
			for (int j = 0; j < tmpFiledList.size(); j++) {
				values[i][j] = tmpFiledList.get(j).getValue(etmp);
			}
			values[i][values[i].length - 1] = Analytic.getPkValue(etmp);
		}
		bsvh.sql = sql.toString();
		bsvh.values = values;
		return bsvh;
	}
	
	/**
	 * description deleteSQL:根据参数 Bean 生成标准删除数据SQL语句(根据主键删除)
	 * <br>author ：koujiang
	 * <br>@param e
	 * <br>@return
	 * <br>@throws IllegalArgumentException
	 * <br>@throws IllegalAccessException 
	 * <br>return :SQLValueHandler
	 * <br>update ：2017年3月13日 下午2:48:57
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public  static final <E> SQLValueHandler deleteSQL(E e) throws IllegalArgumentException, IllegalAccessException {
		if (VNETookit.isNull(e)) return null;

		SQLValueHandler svh = sh.new SQLValueHandler();
		StringBuffer sql = new StringBuffer("del from ");
		sql.append(Analytic.getTabName(e)).append(" where ").append(Analytic.getPkDbName(e));
		sql.append(" = ?");
		svh.sql = sql.toString();
		svh.values = new Object[]{Analytic.getPkValue(e)};
		return svh;
	}
	
	/**
	 * description deleteSQL:根据参数 Bean 生成标准删除数据SQL语句(根据主键删除)
	 * <br>author ：koujiang
	 * <br>@param es
	 * <br>@return
	 * <br>@throws IllegalArgumentException
	 * <br>@throws IllegalAccessException 
	 * <br>return :BatchSQLValueHandler
	 * <br>update ：2017年3月13日 下午2:49:27
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public  static final <E> BatchSQLValueHandler deleteSQL(List<E> es) throws IllegalArgumentException, IllegalAccessException {
		if (VNETookit.isNull(es)) return null;
		E e = es.get(0);
		BatchSQLValueHandler bsvh = sh.new BatchSQLValueHandler();
		StringBuffer sql = new StringBuffer("delete from ");
		sql.append(Analytic.getTabName(e)).append(" where ").append(Analytic.getPkDbName(e));
		sql.append(" = ?");
		bsvh.sql = sql.toString();
		Object[][] arr = new Object[es.size()][1];
		for (int i = 0; i < es.size(); i++) {
			arr[i][0] = Analytic.getPkValue(es.get(i));
		}
		bsvh.values = arr;
		return bsvh;
	}
}
