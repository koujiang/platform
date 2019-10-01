package com.unknown.data.repository;

import com.unknown.data.page.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.Tuple;
import java.util.List;

/**
 * <br>(c) Copyright koujiang901123@sima.com 2018
 * <br>@description	:自定义Repository
 * <br>@file_name	:CustomJpaRepository.java
 * <br>@system_name	:
 * <br>@author		:koujiang
 * <br>@create_time	:2018/12/26 17:37
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
@NoRepositoryBean
public interface CustomCrudRepository {

    <S extends Object> S insert(S entity);

    <S extends Object> List<S> insert(List<S> entitys);

    <S extends Object> S update(S entity, String... attribute);

    <S extends Object> List<S> update(List<S> entitys, String... attribute);

    <S extends Object> void delete(S entity);

    <S extends Object> void delete(List<S> entitys);

    List<Tuple> findTupleQuery(TupleSpecification tupleSpecification);
    Page<Tuple> findTupleQuery(TupleSpecification tupleSpecification, Pageable pageable);
    Object findSQL(String sql, Object... params) throws RuntimeException;

    List findSQLList(String sql, Object... params) throws RuntimeException;

    PageResult<List> findSQLList(String sql, Object[] params, Integer page, Integer size);

    Object findJPQL(String jpql, Object... params) throws RuntimeException;

    List findJPQLList(String jpql, Object... params) throws RuntimeException;

    PageResult<List> findJPQLList(String jpql, Object[] params, Integer page, Integer size);
}
