package com.example.intelligenttransportation.service;

import com.example.intelligenttransportation.entity.Role;

import java.util.List;

/**
 * 角色服务接口
 */
public interface RoleService {
    
    /**
     * 获取所有角色列表
     * @return 角色列表
     */
    List<Role> findAll();
    
    /**
     * 根据ID查询角色
     * @param id 角色ID
     * @return 角色对象
     */
    Role findById(Long id);
    
    /**
     * 根据角色编码查询角色
     * @param roleCode 角色编码
     * @return 角色对象
     */
    Role findByRoleCode(String roleCode);
    
    /**
     * 创建角色
     * @param role 角色对象
     * @return 创建后的角色对象
     */
    Role save(Role role);
    
    /**
     * 更新角色
     * @param role 角色对象
     * @return 更新后的角色对象
     */
    Role update(Role role);
    
    /**
     * 删除角色
     * @param id 角色ID
     */
    void delete(Long id);
    
    /**
     * 更新角色状态
     * @param id 角色ID
     * @param status 状态（0：禁用，1：启用）
     * @return 是否更新成功
     */
    boolean updateStatus(Long id, int status);
    
    /**
     * 为指定角色分配权限
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @return 是否分配成功
     */
    boolean assignPermissions(Long roleId, List<Long> permissionIds);
    
    /**
     * 获取角色的权限ID列表
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> findPermissionIdsByRoleId(Long roleId);
    
    /**
     * 获取用户的角色ID列表
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> findRoleIdsByUserId(Long userId);
    
    /**
     * 获取用户的角色列表
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> findRolesByUserId(Long userId);
} 