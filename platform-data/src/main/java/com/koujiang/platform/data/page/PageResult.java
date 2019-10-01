package com.koujiang.platform.data.page;

import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <br>(c) Copyright koujiang901123@sima.com
 * <br>@description	:分页返回结果封装
 * <br>@file_name	:null.java
 * <br>@system_name	:platform
 * <br>@author		:Administrator
 * <br>@create_time	:2019/2/26 21:51
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
public class PageResult<T> implements Serializable {

    private long totalElements;// 元素个数
    private int totalPages;// 总页数
    private List<T> list = new ArrayList<>();// 包含的数据

    public PageResult()  {
        this.totalElements = 0;
        this.totalPages = 0;
        this.list = null;
    }

    public PageResult(@NonNull Page<T> page)  {
        Objects.isNull(page);
        if (page.getContent() != null && !page.getContent().isEmpty()) {
            list = page.getContent();
            totalElements = page.getTotalElements();
            totalPages = page.getTotalPages();
        }
    }

    public PageResult(long totalElements, int totalPages, List<T> list) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        if (list != null && !list.isEmpty())
            this.list.addAll(list);
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<T> getList() {
        return list;
    }
}
