package com.koujiang.platform.data.repository;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

/**
 * <br>(c) Copyright koujiang901123@sima.com
 * <br>@description	:
 * <br>@file_name	:null.java
 * <br>@system_name	:platform
 * <br>@author		:Administrator
 * <br>@create_time	:2019/3/3 19:51
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
public interface TupleSpecification {

    /**
     * <br>description :拼接SQL
     * <br>@author ：koujiang
     * <br>@param  ：
     * <br>@return ：
     * <br>@update ：2019/3/3 20:08
     * <br>@mender ：(Please add the modifier name)
     * <br>@Modified ：(Please add modification date)
     * <br>@varsion ：v1.0.0
     */
    Predicate[] splice(CriteriaQuery<Tuple> query, CriteriaQuery<Tuple> count, CriteriaBuilder criteriaBuilder);
}
