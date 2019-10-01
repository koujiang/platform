package com.unknown.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * <br>(c) Copyright koujiang901123@sima.com 2018
 * <br>@description	:实体类的基类
 * <br>@file_name	:BaseEntity.java
 * <br>@system_name	:
 * <br>@author		:koujiang
 * <br>@create_time	:2018/12/26 15:36
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public abstract class BaseEntity extends Entity {

    //创建的ID--userID
    @JsonIgnore
    private Long creatorId;
    //创建人的登录名
    @JsonIgnore
    private String creator;
    //修改人的ID--userID
    @JsonIgnore
    private Long modifierId;
    //最后修改的登录名
    @JsonIgnore
    private String modifier;
    //创建时间
    @JsonIgnore
    @CreatedDate
    private LocalDateTime createTime;
    //最后修改时间
    @JsonIgnore
    @LastModifiedDate
    private LocalDateTime modifierTime;

    @JsonIgnore
    private Boolean display = Boolean.TRUE;

    @PrePersist
    public void preCreate() {
        try {
            Subject subject = SecurityUtils.getSubject();
            BaseUser user = (BaseUser) subject.getPrincipal();
            if (user != null) {
                this.creator = user.getUserName();
                this.creatorId = user.getId();
                this.modifier = this.creator;
                this.modifierId = this.creatorId;
            }
        } catch (Exception e) {
            // 定时任务处理方式：
        }
    }

    @PreUpdate
    public void preUpdate() {
        try {
            Subject subject = SecurityUtils.getSubject();
            BaseUser user = (BaseUser) subject.getPrincipal();
            if (user != null) {
                this.modifier = user.getUserName();
                this.modifierId = user.getId();
            }
        } catch (Exception e) {
            // 定时任务处理方式：
        }
    }
}
