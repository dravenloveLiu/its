package com.example.intelligenttransportation.entity;

import lombok.Data;
import java.util.Date;

/**
 * 权限实体类
 */
@Data
public class Permission {
    private Long id;
    private Long parentId;
    private String permissionName;
    private String permissionCode;
    private Integer permissionType;
    private String icon;
    private Integer sort;
    private String path;
    private String component;
    private Integer isFrame;
    private Integer status;
    private Long createUser;
    private Date createTime;
    private Long updateUser;
    private Date updateTime;
    private Integer deleted;
} 