package com.koujiang.platform.core.help;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <br>(c) Copyright 2014 by
 * <br>@description	:开发帮助类
 * <br>@file_name	:HelpTool.java
 * <br>@system_name	:wl_project
 * <br>@author		:koujiang 
 * <br>@create_time	:2014-11-6 下午07:23:23
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
public class HelpTool {
	
	static int i = 0;
	/**初始时间**/
	static long inittime = 0l;
	static long time = 0l;
	/**显示每步时间**/
	public static boolean showtime = true;
	/**显示累计时间**/
	public static boolean showsumtime = false;
	
	/**
	 * description outSQLAndParams:输出SQL和参数
	 * <br>author ：koujiang
	 * <br>@param sql
	 * <br>@param params 
	 * <br>return :void
	 * <br>update ：2014-11-6 下午07:23:34
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	@SuppressWarnings("rawtypes")
	public static void outSQLAndParams(StringBuffer sql, List params) {
		System.out.println(sql.toString());
		if(params != null && !params.isEmpty()) {
			int size = params.size();
			for (int i = 0; i < size; i++) {
				System.out.print(params.get(i)+"\t");
				if((i+1%5)==0) {
					System.out.println();
				}
			}
			System.out.println("\n-------------------------------------------------------------------------------------");
		}
	}
	
	/**
	 * <br>description outSQLAndParamsBytrim:输出SQL和参数   去除参数中的空格
	 * <br>author ：libo
	 * <br>@param sql
	 * <br>@param params 
	 * <br>return :void
	 * <br>update ：2014-11-28 下午08:44:22
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	@SuppressWarnings("rawtypes")
	public static void outSQLAndParamsBytrim(StringBuffer sql, List params) {
		System.out.println(sql.toString());
		if(params != null && !params.isEmpty()) {
			int size = params.size();
			for (int i = 0; i < size; i++) {
				System.out.print("'"+params.get(i).toString().trim()+"'"+"\t");
				if((i+1%5)==0) {
					System.out.println();
				}
			}
			System.out.println("\n-------------------------------------------------------------------------------------");
		}
	}
	
	/**
	 * description outSQLAndParamsBytrimAndFill:输出SQL语句
	 * <br>author ：koujiang
	 * <br>@param sql
	 * <br>@param params 
	 * <br>return :void
	 * <br>update ：2015-10-15 上午10:17:29
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	@SuppressWarnings("rawtypes")
	public static void outSQLAndParamsBytrimAndFill(StringBuffer sql, List params) {
		StringBuffer returnSql=new StringBuffer();
		String[] s=sql.toString().split("[?]");
		if(s.length==params.size()){
			for(int i=0;i<s.length;i++){
				returnSql.append(s[i]+"'"+params.get(i).toString().trim()+"'");
			}
		}else{
			for(int i=0;i<s.length-1;i++){
				returnSql.append(s[i]+"'"+params.get(i).toString().trim()+"'");
			}
			returnSql.append(s[s.length-1]);
		}
		System.out.println(returnSql);
		System.out.println("\n-------------------------------------------------------------------------------------");
	}
	
	/**
	 * description outSQLAndParamsBytrimAndFill:输出SQL语句
	 * <br>author ：koujiang
	 * <br>@param sql
	 * <br>@param params 
	 * <br>return :void
	 * <br>update ：2015-10-15 上午10:17:43
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public static void outSQLAndParamsBytrimAndFill(StringBuffer sql, Object[] params) {
		List<Object> asList = Arrays.asList(params);
		outSQLAndParamsBytrimAndFill(sql, asList);
	}
	
	/**
	 * description reset:重置标记状态
	 * <br>author ：koujiang
	 * <br> 
	 * <br>return :void
	 * <br>update ：2015-10-15 上午11:01:54
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public static void reset() {
		i = 0;
		time = 0l;
		showsumtime = false;
	}
	
	/**
	 * description mark:标记这个时间点(optimize)
	 * <br>author ：koujiang
	 * <br> 
	 * <br>return :void
	 * <br>update ：2015-10-15 上午10:56:46
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public static void mark() {
		long millis = System.currentTimeMillis();
		if(i==0) {
			i+=1;
			inittime = millis;
			time = millis;
			if(showtime) {
				System.out.println(i+":"+millis+"ms"+(showsumtime ? " sumtime:"+(millis-inittime)+"ms" : "")); 
			}
		} else {
			i+=1;
			if(showtime) {System.out.println(i+":"+millis+" difference:"+(millis-time)+"ms"+(showsumtime ? " sumtime:"+(millis-inittime)+"ms" : ""));}
			time = millis;
		}
	}
	
	/**
	 * description mark:标记这个时间点(optimize)
	 * <br>author ：koujiang
	 * <br>@param str 用户定义
	 * <br>return :void
	 * <br>update ：2015-10-15 上午10:57:06
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public static void mark(String str) {
		long millis = System.currentTimeMillis();
		if(i==0) {
			i+=1;
			inittime = millis;
			time = millis;
			if(showtime) {System.out.println(i+","+str+":"+millis+"ms"+(showsumtime ? " sumtime:"+(millis-inittime)+"ms" : ""));}
		} else {
			i+=1;
			if(showtime) {System.out.println(i+","+str+":"+millis+" difference:"+(millis-time)+"ms"+(showsumtime ? " sumtime:"+(millis-inittime)+"ms" : ""));}
			time = millis;
		}
	}

	public static StringBuffer readSQLFile(String pathname) throws RuntimeException {
		File file = new File(pathname);
		boolean execute = file.canExecute();
		if(!execute) throw new RuntimeException("文件路径无效!");
		boolean canRead = file.canRead();
		if(!canRead) throw new RuntimeException("文件不能被读取!");
		StringBuffer sql = new StringBuffer();
		
		BufferedReader reader = null;
		try {
			FileReader file_reader = new FileReader(file);
			reader = new BufferedReader(file_reader);
			String str_line;
			while ((str_line = reader.readLine()) != null) {
				if(str_line != null && str_line.trim().length() > 0) {
					sql.append(str_line.trim()+"  ");
				}
			}
			reader.close();
		} catch (Exception e) {
			
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sql;
    }
}
