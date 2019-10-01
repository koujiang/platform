package com.koujiang.platform.core.calculate;

import com.koujiang.platform.core.util.VNETookit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * <br>(c) Copyright koujiang901123@sina.com
 * <br>@description :用于商业运算(处理数据精度问题)
 *  * <br>提供相对精确的四则运算
 *  * <br>以及基本数据类型的封装数据类型值的有效性(当值为Null则将其值转换成对应的最自然数)
 * <br>@file_name   :MathArith.java
 * <br>@system_name :com.koujiang.platform.core.calculate.MathArith
 * <br>@author      :Administrator
 * <br>@create_time :2019/10/1 23:42
 * <br>@mender      :(Please add the modifier name)
 * <br>@Modified    :(Please add modification date)
 */
public class MathArith {
	
	/**
	 * 默认除法运算精度
	 */
    static final int DEF_DIV_SCALE = 10;
    /**<br>小数<br>##########.##**/
    public static final String FORMATE_DECIMALS = "##########.###";
    /**<br>小数<br>##########.000<br>小数位不够就自动补零**/
    public static final String FORMATE_DECIMALS_ZEROIZE = "##########.000";
    /**<br>整数<br>##########.##**/
    static final String FORMATE_INTEGER = "##########";
    
    /**
     * @description formate:以字符串的形式展示数据,避免科学计数法显示
     * <br>@author ：koujiang
     * <br>@param number
     * <br>@return :String
     * <br>@update ：2014-3-28 下午02:27:00
     * <br>@mender ：(Please add the modifier name)
     * <br>@Modified ：(Please add modification date)
     * <br>@varsion ：v1.0.0
     */
    public static final String formate(Number number) {
		return formate(number, FORMATE_DECIMALS);
    }
    
    /**
     * @description formate:以字符串的形式展示数据,避免科学计数法显示
     * <br>@author ：koujiang
     * <br>@param number
     * <br>@param formate
     * <br>@return :String
     * <br>@update ：2014-3-28 下午02:28:16
     * <br>@mender ：(Please add the modifier name)
     * <br>@Modified ：(Please add modification date)
     * <br>@varsion ：v1.0.0
     */
    public static final String formate(Number number,String formate) {
    	DecimalFormat format = new DecimalFormat(formate);
    	return format.format(number);
    }
    
	/**
	 * @description sub:减法(minuend - subtrahend)
	 * <br>@author ：koujiang
	 * <br>@param minuend 被减数
	 * <br>@param subtrahend 减数
	 * <br>@return :double
	 * <br>@update ：2014-3-25 上午10:56:40
	 * <br>@mender ：(Please add the modifier name)
	 * <br>@Modified ：(Please add modification date)
	 * <br>@varsion ：v1.0.0
	 */
	public static final double sub(double minuend,double subtrahend) {
		BigDecimal minDecimal = new BigDecimal(String.valueOf(minuend));
		BigDecimal subDecimal = new BigDecimal(String.valueOf(subtrahend));
		return minDecimal.subtract(subDecimal).doubleValue();
	}
	
	/**
	 * @description sub:减法(minuend - subtrahend)(字符一定是可以转换成数值的字符)
	 * <br>@author ：koujiang
	 * <br>@param minuend 被减数
	 * <br>@param subtrahend 减数
	 * <br>@return :double
	 * <br>@update ：2014-3-25 上午10:56:40
	 * <br>@mender ：(Please add the modifier name)
	 * <br>@Modified ：(Please add modification date)
	 * <br>@varsion ：v1.0.0
	 */
	public static final double sub(String minuend,String subtrahend) {
		BigDecimal minDecimal = new BigDecimal(minuend);
		BigDecimal subDecimal = new BigDecimal(subtrahend);
		return minDecimal.subtract(subDecimal).doubleValue();
	}

	/**
	 * @description add:加法
	 * <br>@author ：koujiang
	 * <br>@param summand 被加数
	 * <br>@param addend 加数
	 * <br>@return :double
	 * <br>@update ：2014-3-25 上午11:02:16
	 * <br>@mender ：(Please add the modifier name)
	 * <br>@Modified ：(Please add modification date)
	 * <br>@varsion ：v1.0.0
	 */
	public static final double add(double summand,double addend){
        BigDecimal b1 = new BigDecimal(Double.toString(summand));
        BigDecimal b2 = new BigDecimal(Double.toString(addend));
        return b1.add(b2).doubleValue();
    }
	
	/**
	 * @description add:加法(字符一定是可以转换成数值的字符)
	 * <br>@author ：koujiang
	 * <br>@param summand 被加数
	 * <br>@param addend 加数
	 * <br>@return :double
	 * <br>@update ：2014-3-25 上午11:02:16
	 * <br>@mender ：(Please add the modifier name)
	 * <br>@Modified ：(Please add modification date)
	 * <br>@varsion ：v1.0.0
	 */
	public static final double add(String summand,String addend){
        BigDecimal b1 = new BigDecimal(summand);
        BigDecimal b2 = new BigDecimal(addend);
        return b1.add(b2).doubleValue();
    }

	/**
	 * @description add:加法
	 * <br>@author ：koujiang
	 * <br>@param summand 被加数
	 * <br>@param addend 加数
	 * <br>@return 
	 * <br>@return :long
	 * <br>@update ：2014-3-28 上午10:32:38
	 * <br>@mender ：(Please add the modifier name)
	 * <br>@Modified ：(Please add modification date)
	 * <br>@varsion ：v1.0.0
	 */
	public static final long add(long summand,long addend){
        BigDecimal b1 = new BigDecimal(Long.toString(summand));
        BigDecimal b2 = new BigDecimal(Long.toString(addend));
        return b1.add(b2).longValue();
    }
	public static final int add(int summand,int addend){
		BigDecimal b1 = new BigDecimal(Integer.toString(summand));
		BigDecimal b2 = new BigDecimal(Integer.toString(addend));
		return b1.add(b2).intValue();
	}
	
	/**
	 * @description mul:乘法
	 * <br>@author ：koujiang
	 * <br>@param multiplicand 被乘数
	 * <br>@param multiplier 乘数
	 * <br>@return :double
	 * <br>@update ：2014-3-25 上午11:04:33
	 * <br>@mender ：(Please add the modifier name)
	 * <br>@Modified ：(Please add modification date)
	 * <br>@varsion ：v1.0.0
	 */
	public static final double mul(double multiplicand,double multiplier){
        BigDecimal b1 = new BigDecimal(Double.toString(multiplicand));
        BigDecimal b2 = new BigDecimal(Double.toString(multiplier));
        return b1.multiply(b2).doubleValue();
	}

	public static final int mul(int multiplicand,int multiplier){
		BigDecimal b1 = new BigDecimal(Double.toString(multiplicand));
		BigDecimal b2 = new BigDecimal(Double.toString(multiplier));
		return b1.multiply(b2).intValue();
	}
	public static final long mul(long multiplicand,long multiplier){
		BigDecimal b1 = new BigDecimal(Double.toString(multiplicand));
		BigDecimal b2 = new BigDecimal(Double.toString(multiplier));
		return b1.multiply(b2).longValue();
	}

	/**
	 * @description mul:乘法
	 * <br>@author ：koujiang
	 * <br>@param multiplicand 被乘数
	 * <br>@param multiplier 乘数
	 * <br>@return :double
	 * <br>@update ：2014-3-25 上午11:04:33
	 * <br>@mender ：(Please add the modifier name)
	 * <br>@Modified ：(Please add modification date)
	 * <br>@varsion ：v1.0.0
	 */
	public static final double mul(double multiplicand,double multiplier,int scale){
		BigDecimal b1 = new BigDecimal(Double.toString(multiplicand));
		BigDecimal b2 = new BigDecimal(Double.toString(multiplier));
		return round(b1.multiply(b2).doubleValue(), scale);
	}

	/**
	 * @description div:除法
	 * <br>@author ：koujiang
	 * <br>@param dividend 被除数
	 * <br>@param divisor 除数
	 * <br>@return :double
	 * <br>@update ：2014-3-25 上午11:10:26
	 * <br>@mender ：(Please add the modifier name)
	 * <br>@Modified ：(Please add modification date)
	 * <br>@varsion ：v1.0.0
	 */
	public static final double div(double dividend,double divisor){
		return div(dividend,divisor,DEF_DIV_SCALE);
	}
	
	/**
	 * @description div:除法
	 * <br>@author ：koujiang
	 * <br>@param dividend 被除数
	 * <br>@param divisor 除数
	 * <br>@param scale 精确到小数点以后几位
	 * <br>@return :double
	 * <br>@update ：2014-3-25 上午11:09:51
	 * <br>@mender ：(Please add the modifier name)
	 * <br>@Modified ：(Please add modification date)
	 * <br>@varsion ：v1.0.0
	 */
	public static final double div(double dividend,double divisor,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(dividend));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
	
	public static final double div(String dividend,String divisor,int scale){
		if(scale<0){
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(dividend);
		BigDecimal b2 = new BigDecimal(divisor);
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static final double div(String dividend,double divisor,int scale){
		if(scale<0){
			throw new IllegalArgumentException(
			"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(dividend);
		BigDecimal b2 = new BigDecimal(Double.toString(divisor));
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static final double div(double dividend,String divisor,int scale){
		if(scale<0){
			throw new IllegalArgumentException(
			"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(dividend));
		BigDecimal b2 = new BigDecimal(divisor);
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * @description round:四舍五入
	 * <br>@author ：koujiang
	 * <br>@param v 需要处理的数
	 * <br>@param scale
	 * <br>@return :double
	 * <br>@update ：2014-3-25 上午11:11:56
	 * <br>@mender ：(Please add the modifier name)
	 * <br>@Modified ：(Please add modification date)
	 * <br>@varsion ：v1.0.0
	 */
	public static final double round(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
	
	/**
	 * @description getVal:基本数据类型转换
	 * <br>@author ：koujiang
	 * <br>@param <T> 
	 * <br>@param val
	 * <br>@param attrs
	 * <br> Integer.class,Boolean.class,Long.class,Double.class...基本数据类型的Class对象,
	 * <br>不能使用如:Double.TYPE...
	 * <br> 可以填写并且是唯一的引用数据类型String.class
	 * <br>@throws Throwable 
	 * <br>@return :T
	 * <br>@update ：2014-4-22 下午05:28:51
	 * <br>@mender ：(Please add the modifier name)
	 * <br>@Modified ：(Please add modification date)
	 * <br>@varsion ：v1.0.0
	 */
	public static <T> T getVal(String val, Class<T> attrs) throws Exception {
		Constructor<T> constructor = attrs.getConstructor(String.class);
		return constructor.newInstance(val);
	}
	/**
	 * @description printNotNullValue:输出对象不为空的属性值(Text)
	 * <br>@author ：koujiang
	 * <br>@param object 
	 * <br>@return :void
	 * <br>@update ：2014-5-5 下午09:04:04
	 * <br>@mender ：(Please add the modifier name)
	 * <br>@Modified ：(Please add modification date)
	 * <br>@varsion ：v1.0.0
	 */
	public static void printNotNullValue(Object object) {
		try {
			Class<? extends Object> class1 = object.getClass();
			Field[] fields = class1.getDeclaredFields();
			int k = 0;
			int length = fields.length;
			for (int i = 0; i < length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				Object object2 = field.get(object);
				if(object2 != null) {
					System.out.print(field.getName() + "=" + object2+"    ");
					k++;
				}
				if(k != 0 && k%5 == 0)
					System.out.println("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * description computeVariance:计算方差
	 * <br>author ：koujiang
	 * <br>@param arrays		计算的数据源
	 * <br>return :Double
	 * <br>update ：2014-8-15 下午04:11:39
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public static Double computeVariance(double[] arrays) {
		if(arrays != null && arrays.length > 0) {
			int length = arrays.length;
			Double avg = 0.0;
			Double sum = 0.0;
			for (int i = 0; i < length; i++) {
				sum = MathArith.add(sum, VNETookit.isNull(arrays[i])? 0.0 : arrays[i]);
			}
			avg = MathArith.div(sum, length);
			Double powsum = 0.0;
			for (int i = 0; i < length; i++) {
				powsum = MathArith.add(powsum, Math.pow((VNETookit.isNull(arrays[i])? 0.0 : arrays[i]) - avg, 2));
			}
			return MathArith.div(powsum, length);
		}
		return null;
	}
	
	/**
	 * description computeVariance:计算方差
	 * <br>author ：koujiang
	 * <br>@param arrays		计算的数据源
	 * <br>return :Double
	 * <br>update ：2014-8-15 下午04:11:39
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public static Double computeVariance(Double[] arrays) {
		if(arrays != null && arrays.length > 0) {
			int length = arrays.length;
			Double avg = 0.0;
			Double sum = 0.0;
			for (int i = 0; i < length; i++) {
				sum = MathArith.add(sum, VNETookit.isNull(arrays[i])? 0.0 : arrays[i]);
			}
			avg = MathArith.div(sum, length);
			Double powsum = 0.0;
			for (int i = 0; i < length; i++) {
				powsum = MathArith.add(powsum, Math.pow((VNETookit.isNull(arrays[i])? 0.0 : arrays[i]) - avg, 2));
			}
			return MathArith.div(powsum, length);
		}
		return null;
	}

	/**
	 * description judgeStabilization:判断数值是不是在一平均值内波动,波动范围为range
	 * <br>author ：koujiang
	 * <br>@param arrays
	 * <br>@param range
	 * <br>@return 
	 * <br>return :boolean
	 * <br>update ：2014-8-15 下午04:34:58
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public static boolean judgeStabilization(Double[] arrays, double range) {
		if(arrays != null && arrays.length > 0) {
			int length = arrays.length;
			Double avg = 0.0;
			Double sum = 0.0;
			for (int i = 0; i < length; i++) {
				sum = MathArith.add(sum, VNETookit.isNull(arrays[i])? 0.0 : arrays[i]);
			}
			avg = MathArith.div(sum, length);
			for (int i = 0; i < length; i++) {
				if(Math.abs(MathArith.sub(arrays[i], avg)) > range) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * description getConversionVal:获取数字的大写
	 * <br>author ：koujiang
	 * <br>@param dou
	 * <br>@return 
	 * <br>return :String
	 * <br>update ：2014-10-30 下午08:48:47
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public static String getConversionVal(Double dou) {
		if(dou == null || dou < 0.0) {
			return "";
		}
		String of = String.valueOf(dou);
		if(of.length() > 13) {
			throw new RuntimeException("数字长度过长");
		}
		return cleanZero(splitNum(formate(round(dou, 2), "##########.00")));
	}
	
	/**
	 * description splitNum:以小数点为界分割开来,进行相应的中文金额大写形式的转换
	 * <br>author ：koujiang
	 * <br>@param s
	 * <br>@return 
	 * <br>return :String
	 * <br>update ：2014-10-31 下午05:16:46
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	private static String splitNum(String s) {
		int index = s.indexOf(".");
		String intOnly = s.substring(0, index);
		String part1 = numFormat(1, intOnly);
		String smallOnly = s.substring(index + 1);
		String part2 = numFormat(2, smallOnly);
		String newS = part1 + part2;
		return newS;
	}
	
	/**
	 * description numFormat:把传入的数转换为中文金额大写形式
	 * <br>author ：koujiang
	 * <br>@param flag
	 * <br>@param s
	 * <br>@return 
	 * <br>return :String
	 * <br>update ：2014-10-30 下午08:41:36
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	private static String numFormat(int flag, String s) {
		int sLength = s.length();
		String bigLetter[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
		String unit[] = {"元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万"};
		String small[] = {"分", "角"};
		String newS = "";
		for(int i = 0; i < sLength; i ++) {
			if(flag == 1) {
				newS = newS + bigLetter[s.charAt(i) - 48] + unit[sLength - i - 1];
			} else if(flag == 2) {
				newS = newS + bigLetter[s.charAt(i) - 48] + small[sLength - i - 1];
			}
		}
		return newS;
	}
	
	/**
	 * description cleanZero:清理这个字符串里面多余的零
	 * <br>author ：koujiang
	 * <br>@param s
	 * <br>@return 
	 * <br>return :String
	 * <br>update ：2014-10-30 下午08:41:17
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	private static String cleanZero(String s) {
		while(s.charAt(0) == '零') {
			s = s.substring(2);
			if(s.length() == 0) {
				return "零";
			}
		}
		String regex1[] = {"零仟", "零佰", "零拾"};
		String regex2[] = {"零亿", "零万", "零元"};
		String regex3[] = {"亿", "万", "元"};
		String regex4[] = {"零角", "零分"};
		for(int i = 0; i < 3; i ++) {
			s = s.replaceAll(regex1[i], "零");
		}
		for(int i = 0; i < 3; i ++) {
			s = s.replaceAll("零零零", "零");
			s = s.replaceAll("零零", "零");
			s = s.replaceAll(regex2[i], regex3[i]);
		}
		for(int i = 0; i < 2; i ++) {
			s = s.replaceAll(regex4[i], "");
		}
		s = s.replaceAll("亿万", "亿");
		return s;
	}

	public static final double add(String summand,Number addend){
		BigDecimal b1 = new BigDecimal(summand);
		BigDecimal b2 = new BigDecimal(addend.toString());
		return b1.add(b2).doubleValue();
	}
}
