package com.example.intelligenttransportation.service.impl;

import com.example.intelligenttransportation.entity.Role;
import com.example.intelligenttransportation.mapper.RoleMapper;
import com.example.intelligenttransportation.mapper.RolePermissionMapper;
import com.example.intelligenttransportation.mapper.UserRoleMapper;
import com.example.intelligenttransportation.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 角色服务实现类
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleMapper.findById(id);
    }

    @Override
    public Role findByRoleCode(String roleCode) {
        return roleMapper.findByRoleCode(roleCode);
    }

    @Override
    @Transactional
    public Role save(Role role) {
        role.setCreateTime(new Date());
        roleMapper.insert(role);
        return role;
    }

    @Override
    @Transactional
    public Role update(Role role) {
        role.setUpdateTime(new Date());
        roleMapper.update(role);
        return role;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // 删除角色
        roleMapper.deleteById(id);
        // 删除角色权限关联
        rolePermissionMapper.deleteByRoleId(id);
        // 删除用户角色关联
        // 注意：一般来说，删除角色时不应该影响到用户，所以这一步可以根据业务需求决定是否执行
        // userRoleMapper.deleteByRoleId(id);
    }

    @Override
    @Transactional
    public boolean updateStatus(Long id, int status) {
        return roleMapper.updateStatus(id, status) > 0;
    }

    @Override
    @Transactional
    public boolean assignPermissions(Long roleId, List<Long> permissionIds) {
        // 先删除角色当前权限
        rolePermissionMapper.deleteByRoleId(roleId);
        
        // 如果权限ID列表为空，表示清空角色权限，直接返回成功
        if (permissionIds == null || permissionIds.isEmpty()) {
            return true;
        }
        
        // 批量分配新权限
        return rolePermissionMapper.insertBatch(roleId, permissionIds) > 0;
    }

    @Override
    public List<Long> findPermissionIdsByRoleId(Long roleId) {
        return rolePermissionMapper.findPermissionIdsByRoleId(roleId);
    }

    @Override
    public List<Long> findRoleIdsByUserId(Long userId) {
        return userRoleMapper.findRoleIdsByUserId(userId);
    }

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        return roleMapper.findRolesByUserId(userId);
    }
} 