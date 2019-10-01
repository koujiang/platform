package com.koujiang.platform.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * <br>(c) Copyright koujiang901123@sima.com 2018
 * <br>@description	:全局JPA Repository
 * <br>@file_name	:CustomJpaRepository.java
 * <br>@system_name	:
 * <br>@author		:koujiang
 * <br>@create_time	:2018/12/26 17:37
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
