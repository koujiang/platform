package com.unknown.data.repository;

import com.unknown.data.page.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * <br>(c) Copyright koujiang901123@sima.com
 * <br>@description	:
 * <br>@file_name	:null.java
 * <br>@system_name	:platform
 * <br>@author		:Administrator
 * <br>@create_time	:2019/3/13 20:51
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
@Repository
public class CustomSimpleRepository<T, ID> implements CustomCrudRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private int batchSize = 5000;

    @Override
    public List<Tuple> findTupleQuery(TupleSpecification tupleSpecification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        Predicate[] splice = tupleSpecification.splice(query, null, criteriaBuilder);
        return entityManager.createQuery(query.where(splice)).getResultList();
    }

    public Page<Tuple> findTupleQuery(TupleSpecification tupleSpecification, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        CriteriaQuery<Tuple> count = criteriaBuilder.createTupleQuery();
        Predicate[] splice = tupleSpecification.splice(query, count, criteriaBuilder);
        TypedQuery<Tuple> queryTuple = entityManager.createQuery(query.where(splice));
        List resultList = queryTuple.setFirstResult((int)pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        TypedQuery<Tuple> countTuple = entityManager.createQuery(count.where(splice));
        Long totalElements = (Long) countTuple.getSingleResult().get(0);
        return new PageImpl(resultList, pageable, totalElements.intValue());
    }
    
    @Override
    public Object findSQL(String sql, Object... params) throws RuntimeException {
        Query query = entityManager.createNativeQuery(sql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i+1, params[i]);
            }
        }
        return query.getSingleResult();
    }

    @Override
    public List findSQLList(String sql, Object... params) throws RuntimeException {
        Query query = entityManager.createNativeQuery(sql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i+1, params[i]);
            }
        }
        return query.getResultList();
    }

    @Override
    public PageResult<List> findSQLList(String sql, Object[] params, Integer page, Integer size) {
        Query query = entityManager.createNativeQuery(sql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i+1, params[i]);
            }
        }
        int totalElements = query.getMaxResults();
        int totalPages = (totalElements / size) + (totalElements % size != 0 ? 1 : 0);
        List resultList = query.setFirstResult(page * size).setMaxResults(size).getResultList();
        return new PageResult(totalElements,totalPages,resultList);
    }

    @Override
    public Object findJPQL(String jpql, Object... params) throws RuntimeException {
        Query query = entityManager.createQuery(jpql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i+1, params[i]);
            }
        }
        return query.getSingleResult();
    }

    @Override
    public List findJPQLList(String jpql, Object... params) throws RuntimeException {
        Query query = entityManager.createQuery(jpql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i+1, params[i]);
            }
        }
        return query.getResultList();
    }

    @Override
    public PageResult<List> findJPQLList(String jpql, Object[] params, Integer page, Integer size) {
        Query query = entityManager.createQuery(jpql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i+1, params[i]);
            }
        }
        int totalElements = query.getMaxResults();
        int totalPages = (totalElements / size) + (totalElements % size != 0 ? 1 : 0);
        List resultList = query.setFirstResult(page * size).setMaxResults(size).getResultList();
        return new PageResult(totalElements,totalPages,resultList);
    }

    @Override
    @Transactional
    public <S extends Object> S insert(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public <S extends Object> List<S> insert(List<S> entitys) {
        int size = entitys.size();
        for (int i = 0; i < size; i++) {
            entityManager.persist(entitys.get(i));
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.close();
            }
        }
        if (size % batchSize != 0) {
            entityManager.flush();
            entityManager.close();
        }
        return entitys;
    }

    @Override
    @Transactional
    public <S extends Object> S update(S entity, String... attribute) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    @Transactional
    public <S extends Object> List<S> update(List<S> entitys, String... attribute) {
        int size = entitys.size();
        for (int i = 0; i < size; i++) {
            entityManager.merge(entitys.get(i));
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        if (size % batchSize != 0) {
            entityManager.flush();
            entityManager.close();
        }
        return entitys;
    }

    @Override
    @Transactional
    public <S extends Object> void delete(S entity) {
    }

    @Override
    @Transactional
    public <S extends Object> void delete(List<S> entitys) {
    }
}
