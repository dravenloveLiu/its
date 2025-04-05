package com.example.intelligenttransportation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.intelligenttransportation.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色数据访问接口
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    
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
    Role findById(@Param("id") Long id);
    
    /**
     * 根据角色编码查询角色
     * @param roleCode 角色编码
     * @return 角色对象
     */
    Role findByRoleCode(@Param("roleCode") String roleCode);
    
    /**
     * 插入角色
     * @param role 角色对象
     * @return 影响行数
     */
    int insert(Role role);
    
    /**
     * 更新角色
     * @param role 角色对象
     * @return 影响行数
     */
    int update(Role role);
    
    /**
     * 删除角色（逻辑删除）
     * @param id 角色ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 更新角色状态
     * @param id 角色ID
     * @param status 状态（0：禁用，1：启用）
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") int status);
    
    /**
     * 根据用户ID查询角色列表
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> findRolesByUserId(@Param("userId") Long userId);
} 