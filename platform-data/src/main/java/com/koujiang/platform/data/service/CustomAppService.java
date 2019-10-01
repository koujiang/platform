package com.koujiang.platform.data.service;

import com.koujiang.platform.data.jdbc.SQLOperate;
import com.koujiang.platform.data.page.PageResult;
import com.koujiang.platform.data.repository.CustomCrudRepository;
import com.koujiang.platform.data.repository.TupleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <br>(c) Copyright koujiang901123@sima.com
 * <br>@description	:
 * <br>@file_name	:null.java
 * <br>@system_name	:platform
 * <br>@author		:Administrator
 * <br>@create_time	:2019/3/14 9:43
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
@Service
public class CustomAppService {

    @Autowired
    private CustomCrudRepository customCrudRepository;
    @Autowired
    private SQLOperate sqlOperate;

    public List<Tuple> findTupleQuery(TupleSpecification tupleSpecification) {
        return customCrudRepository.findTupleQuery(tupleSpecification);
    }

    public List findJPQLList(String jpql, Object... params) throws RuntimeException {
        return customCrudRepository.findJPQLList(jpql, params);
    }

    public List<Map<String, Object>> findSQLForMap(String sql, List<Object> params) {
        return sqlOperate.querySQLForMap(sql, params.toArray());
    }
    public List<Map<String, Object>> findSQLForMap(String sql, Object... params) {
        return sqlOperate.querySQLForMap(sql, params);
    }

    public Map<String, Object> findSQLMap(String sql, Object... params) {
        return sqlOperate.querySQLMap(sql, params);
    }

    public PageResult<Map<String, Object>> findSQLForMap(String sql, List<Object> params, Integer page, Integer size) {
        //查询分页数据
        StringBuffer pageSQL = new StringBuffer(sql);
        pageSQL.append(" limit ?, ? ");
        List<Object> pageParams = new ArrayList();
        if (params != null && !params.isEmpty())
            pageParams.addAll(params);
        pageParams.add(page * size);
        pageParams.add(size);
        List<Map<String, Object>> mapList = sqlOperate.querySQLForMap(pageSQL.toString(), pageParams.toArray());
        //查询统计数据
        String sub = sql.substring(sql.indexOf("select")+6, sql.lastIndexOf("from"));
        //String countSQL = sql.replace(sub, " count("+sub.split(",")[0].trim()+") ");
        String countSQL = sql.replace(sub, " count(*) ");
        int count = 0;
        if (params != null && !params.isEmpty()) {
            List<Object> countParams = new ArrayList(params);
            count = sqlOperate.count(countSQL, countParams.toArray());
        } else {
            count = sqlOperate.count(countSQL);
        }
        return new PageResult(count, (count / size)+(count % size> 0 ? 1 : 0), mapList);
    }

    public Double queryForDouble(String sql, Object... params) throws RuntimeException {
        return sqlOperate.queryForDouble(sql, params);
    }

    public boolean exist(String sql, Object... params) throws RuntimeException {
        int count = sqlOperate.count(sql, params);
        return count > 0;
    }
}
