package com.unknown.platform.mongo.data;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <br>(c) Copyright koujiang901123@sina.com
 * <br>@description	:
 * <br>@file_name	:CustomMongoRepository.java
 * <br>@system_name	:com.unknown.platform.data.CustomMongoRepository
 * <br>@author		:koujiang
 * <br>@create_time	:2019/9/10 17:41
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 */
public interface CustomMongoRepository<T,ID> extends MongoRepository<T,ID> {
}
