package com.example.intelligenttransportation.service;

import com.example.intelligenttransportation.entity.SysUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象
     */
    SysUser findByUsername(String username);
    
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
    SysUser findById(Long id);
    
    /**
     * 创建用户
     * @param user 用户对象
     * @return 创建后的用户对象
     */
    SysUser save(SysUser user);
    
    /**
     * 更新用户
     * @param user 用户对象
     * @return 更新后的用户对象
     */
    SysUser update(SysUser user);
    
    /**
     * 删除用户
     * @param id 用户ID
     */
    void delete(Long id);
    
    /**
     * 更新用户状态
     * @param id 用户ID
     * @param status 状态（0：禁用，1：启用）
     * @return 是否更新成功
     */
    boolean updateStatus(Long id, int status);
    
    /**
     * 重置用户密码
     * @param id 用户ID
     * @param newPassword 新密码
     * @return 是否重置成功
     */
    boolean resetPassword(Long id, String newPassword);
    
    /**
     * 为指定用户分配角色
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     * @return 是否分配成功
     */
    boolean assignRoles(Long userId, List<Long> roleIds);
} 