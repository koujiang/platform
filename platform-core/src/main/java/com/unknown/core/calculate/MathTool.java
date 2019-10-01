package com.unknown.core.calculate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <br>(c) Copyright koujiang901123@sina.com
 * <br>@description	:数学工具
 * <br>@file_name	:MathTool.java
 * <br>@system_name	:com.unknown.platform.util.math.MathTool
 * <br>@author		:koujiang
 * <br>@create_time	:2019/8/28 18:59
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 */
public class MathTool {

    //获取组合(从集合中获取多少个的组合)
    public static <E> List<List<E>> combineByNum(List<E> details, int num) {
        if (num < 1 || details == null || details.isEmpty())
            return null;
        List<List<E>> arrayList = new ArrayList<>();
        combinerSelect(details, new ArrayList<>(), num,arrayList);
        return arrayList;
    }
    
    /**
     * <br>description :无重复有序组合
     * <br>@author ：koujiang
     * <br>@param  ：
     * <br>@return ：
     * <br>@update ：2019/1/10 10:38
     * <br>@mender ：(Please add the modifier name)
     * <br>@Modified ：(Please add modification date)
     * <br>@varsion ：v1.0.0
     */
    public static <E> void combinerSelect(List<E> data, List<E> workSpace, int k, List<List<E>> arrarrList) {
        ArrayList<E> copyData;
        ArrayList<E> copyWorkSpace;

        if(workSpace.size() == k) {
            arrarrList.add(workSpace);
        }

        for(int i = 0; i < data.size(); i++) {
            copyData = new ArrayList<E>(data);
            copyWorkSpace = new ArrayList<E>(workSpace);

            copyWorkSpace.add(copyData.get(i));
            for(int j = i; j >=  0; j--)
                copyData.remove(j);
            combinerSelect(copyData, copyWorkSpace, k, arrarrList);
        }
    }

    //获取一个随机数(不包含max)
    public static Integer getRandom(int min, int max) {
        double random = Math.random();
        int num = (int) (random * (max - min)) + min;

        System.out.println(random+"-----"+num);

        return Integer.valueOf(num);
    }

    //获取指定个数的随机数(至少取一个)
    public static List<Integer> getRandom(int min, int max, int num) {
        if (num == 0)
            num = 1;
        Set<Integer> set = new HashSet<>();
        while (set.size() < num) {
            Integer random = getRandom(min, max);
            set.add(random);
        }
        return new ArrayList<>(set);
    }
}
