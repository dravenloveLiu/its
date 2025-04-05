package com.example.intelligenttransportation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色权限关联数据访问接口
 */
@Mapper
public interface RolePermissionMapper {
    
    /**
     * 根据角色ID查询权限ID列表
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> findPermissionIdsByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 根据角色ID删除角色权限关联
     * @param roleId 角色ID
     * @return 影响行数
     */
    int deleteByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 删除指定角色和权限的关联
     * @param roleId 角色ID
     * @param permissionId 权限ID
     * @return 影响行数
     */
    int deleteByRoleIdAndPermissionId(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
    
    /**
     * 批量插入角色权限关联
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @return 影响行数
     */
    int insertBatch(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);
    
    /**
     * 插入角色权限关联
     * @param roleId 角色ID
     * @param permissionId 权限ID
     * @return 影响行数
     */
    int insert(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
    
    /**
     * 根据权限ID删除角色权限关联
     * @param permissionId 权限ID
     * @return 影响行数
     */
    int deleteByPermissionId(@Param("permissionId") Long permissionId);
} 