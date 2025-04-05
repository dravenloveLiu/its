package com.example.intelligenttransportation.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.intelligenttransportation.entity.Permission;
import com.example.intelligenttransportation.entity.Role;
import com.example.intelligenttransportation.entity.SysUser;
import com.example.intelligenttransportation.mapper.PermissionMapper;
import com.example.intelligenttransportation.mapper.RoleMapper;
import com.example.intelligenttransportation.mapper.UserMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义UserDetailsService实现
 * 用于加载用户信息和权限信息
 */
@Service("securityUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;

    public UserDetailsServiceImpl(UserMapper userMapper, RoleMapper roleMapper, PermissionMapper permissionMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户
        SysUser user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        // 用户被禁用
        if (user.getStatus() != 1) {
            throw new UsernameNotFoundException("用户已被禁用: " + username);
        }

        // 获取用户角色
        List<Role> roles = roleMapper.findRolesByUserId(user.getId());
        // 获取用户权限
        List<Permission> permissions = permissionMapper.selectPermissionsByUserId(user.getId());

        // 构建授权信息
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        // 添加角色，需要添加前缀 ROLE_
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleCode()));
        }
        
        // 添加权限
        List<SimpleGrantedAuthority> permissionAuthorities = permissions.stream()
                .filter(permission -> permission.getPermissionCode() != null && !permission.getPermissionCode().isEmpty())
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionCode()))
                .collect(Collectors.toList());
        authorities.addAll(permissionAuthorities);

        // 返回UserDetails实现
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,                 // 是否启用
                true,                 // 账号是否未过期
                true,                 // 凭证是否未过期
                true,                 // 账号是否未锁定
                authorities           // 权限列表
        );
    }
} 