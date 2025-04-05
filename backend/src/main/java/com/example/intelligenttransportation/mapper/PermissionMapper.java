package com.example.intelligenttransportation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.intelligenttransportation.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 权限数据访问接口
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 获取所有权限
     * @return 权限列表
     */
    List<Permission> findAll();
    
    /**
     * 根据ID查询权限
     * @param id 权限ID
     * @return 权限对象
     */
    Permission findById(@Param("id") Long id);
    
    /**
     * 根据权限编码查询权限
     * @param permissionCode 权限编码
     * @return 权限对象
     */
    Permission findByPermissionCode(@Param("permissionCode") String permissionCode);
    
    /**
     * 插入权限
     * @param permission 权限对象
     * @return 影响行数
     */
    int insert(Permission permission);
    
    /**
     * 更新权限
     * @param permission 权限对象
     * @return 影响行数
     */
    int update(Permission permission);
    
    /**
     * 删除权限
     * @param id 权限ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据角色ID查询权限
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<Permission> selectPermissionsByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 根据角色ID查询权限代码列表
     * @param roleId 角色ID
     * @return 权限代码列表
     */
    List<String> selectPermissionCodesByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 根据用户ID查询权限
     * @param userId 用户ID
     * @return 权限列表
     */
    List<Permission> selectPermissionsByUserId(@Param("userId") Long userId);
    
    /**
     * 根据用户ID查询权限代码列表
     * @param userId 用户ID
     * @return 权限代码列表
     */
    List<String> selectPermissionCodesByUserId(@Param("userId") Long userId);
} 