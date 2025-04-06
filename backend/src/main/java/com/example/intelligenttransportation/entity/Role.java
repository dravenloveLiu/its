package com.example.intelligenttransportation.entity;

import lombok.Data;
import java.util.Date;

/**
 * 角色实体类
 */
@Data
public class Role {
    
    /**
     * 角色ID
     */
    private Long id;
    
    /**
     * 角色名称
     */
    private String roleName;
    
    /**
     * 角色代码
     */
    private String roleCode;
    
    /**
     * 角色描述
     */
    private String description;
    
    /**
     * 创建者ID
     */
    private Long createUser;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新者ID
     */
    private Long updateUser;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 逻辑删除标志（0：未删除，1：已删除）
     */
    private Integer deleted;
} 