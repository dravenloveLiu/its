package com.example.intelligenttransportation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色关联数据访问接口
 */
@Mapper
public interface UserRoleMapper {
    
    /**
     * 根据用户ID查询角色ID列表
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> findRoleIdsByUserId(@Param("userId") Long userId);
    
    /**
     * 根据用户ID删除用户角色关联
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteByUserId(@Param("userId") Long userId);
    
    /**
     * 删除指定用户和角色的关联
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 影响行数
     */
    int deleteByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);
    
    /**
     * 批量插入用户角色关联
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     * @return 影响行数
     */
    int insertBatch(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
    
    /**
     * 插入用户角色关联
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 影响行数
     */
    int insert(@Param("userId") Long userId, @Param("roleId") Long roleId);
} 