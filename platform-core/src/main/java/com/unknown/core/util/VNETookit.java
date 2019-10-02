package com.unknown.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * <br>(c) Copyright 2017 koujiang901123@sina.com
 * <br>@description	:Validation is not empty (验证是否为空)
 * <br> 基础数据类型不做处理
 * <br> 对象数据类型
 * <br>@file_name	:VNEUtil.java
 * <br>@author		:koujiang 
 * <br>@create_time	:2017年3月10日 下午5:30:38
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
public class VNETookit {

	/**
	 * description isNull:非空验证
	 * <br>author ：koujiang
	 * <br>@param value
	 * <br>@return true:空;false:非空
	 * <br>return :boolean
	 * <br>update ：2017年3月10日 下午5:21:59
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public static final boolean isNull(String arg) {
		//(str == null || "".equals(str))
		return null == arg || arg.trim().length() == 0;
	}
	
	/**
	 * description isNull:非空验证
	 * <br>author ：koujiang
	 * <br>@param arg
	 * <br>return :boolean true:空;false:非空
	 * <br>update ：2017-3-20 上午11:15:25
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static final boolean isNull(Object arg) {
		if(arg instanceof Collection) {
			Collection<Object> collection = (Collection<Object>) arg;
			return isNull(collection);
		} else if(arg instanceof Map) {
			Map<Object, Object> args = (Map<Object, Object>) arg;
			return isNull(args);
		}
		return null == arg || isNull(arg.toString());
	}
	
	/**
	 * description isNull:非空验证
	 * <br>author ：koujiang
	 * <br>@param args
	 * <br>return :boolean true:空;false:非空
	 * <br>update ：2017-3-20 上午11:15:30
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	private static final boolean isNull(Collection<Object> args) {
		return null == args || args.isEmpty();
	}
	
	/**
	 * description isNull:非空验证
	 * <br>author ：koujiang
	 * <br>@param args
	 * <br>return :boolean true:空;false:非空
	 * <br>update ：2017-3-20 上午11:15:35
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	private static final boolean isNull(Map<Object, Object> args) {
		return null == args || args.isEmpty();
	}
	
	/**数组验证**/
	public static final boolean isNull(long[] args) {
		return null == args || args.length == 0;
	}
	public static final boolean isNull(int[] args) {
		return null == args || args.length == 0;
	}
	public static final boolean isNull(short[] args) {
		return null == args || args.length == 0;
	}
	public static final boolean isNull(char[] args) {
		return null == args || args.length == 0;
	}
	public static final boolean isNull(byte[] args) {
		return null == args || args.length == 0;
	}
	public static final boolean isNull(boolean[] args) {
		return null == args || args.length == 0;
	}
	public static final boolean isNull(float[] args) {
		return null == args || args.length == 0;
	}
	public static final boolean isNull(double[] args) {
		return null == args || args.length == 0;
	}
	public static final boolean isNull(Object[] args) {
		return null == args || args.length == 0;
	}
}
