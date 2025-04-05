package com.example.intelligenttransportation.entity;

import lombok.Data;
import java.util.Date;

/**
 * 用户实体类
 */
@Data
public class SysUser {
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 真实姓名
     */
    private String realName;
    
    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 状态（0：禁用，1：启用）
     */
    private Integer status;
    
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