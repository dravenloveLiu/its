package com.example.intelligenttransportation.service.impl;

import com.example.intelligenttransportation.entity.SysUser;
import com.example.intelligenttransportation.mapper.UserMapper;
import com.example.intelligenttransportation.mapper.UserRoleMapper;
import com.example.intelligenttransportation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserRoleMapper userRoleMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SysUser findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public List<SysUser> findAll() {
        return userMapper.findAll();
    }

    @Override
    public SysUser findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    @Transactional
    public SysUser save(SysUser user) {
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(new Date());
        userMapper.insert(user);
        return user;
    }

    @Override
    @Transactional
    public SysUser update(SysUser user) {
        user.setUpdateTime(new Date());
        userMapper.update(user);
        return user;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // 逻辑删除
        userMapper.deleteById(id);
        // 删除用户角色关联
        userRoleMapper.deleteByUserId(id);
    }

    @Override
    @Transactional
    public boolean updateStatus(Long id, int status) {
        return userMapper.updateStatus(id, status) > 0;
    }

    @Override
    @Transactional
    public boolean resetPassword(Long id, String newPassword) {
        // 加密新密码
        String encodedPassword = passwordEncoder.encode(newPassword);
        return userMapper.updatePassword(id, encodedPassword) > 0;
    }

    @Override
    @Transactional
    public boolean assignRoles(Long userId, List<Long> roleIds) {
        // 先删除用户当前角色
        userRoleMapper.deleteByUserId(userId);
        
        // 如果角色ID列表为空，表示清空用户角色，直接返回成功
        if (roleIds == null || roleIds.isEmpty()) {
            return true;
        }
        
        // 批量分配新角色
        return userRoleMapper.insertBatch(userId, roleIds) > 0;
    }
} 