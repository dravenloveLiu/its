package com.example.intelligenttransportation.mapper;

import com.example.intelligenttransportation.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户数据访问接口
 */
@Mapper
public interface UserMapper {
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象
     */
    SysUser findByUsername(@Param("username") String username);
    
    /**
     * 获取所有用户列表
     * @return 用户列表
     */
    List<SysUser> findAll();
    
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户对象
     */
    SysUser findById(@Param("id") Long id);
    
    /**
     * 插入用户
     * @param user 用户对象
     * @return 影响行数
     */
    int insert(SysUser user);
    
    /**
     * 更新用户
     * @param user 用户对象
     * @return 影响行数
     */
    int update(SysUser user);
    
    /**
     * 删除用户（逻辑删除）
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 更新用户状态
     * @param id 用户ID
     * @param status 状态（0：禁用，1：启用）
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") int status);
    
    /**
     * 更新用户密码
     * @param id 用户ID
     * @param password 加密后的密码
     * @return 影响行数
     */
    int updatePassword(@Param("id") Long id, @Param("password") String password);
} 