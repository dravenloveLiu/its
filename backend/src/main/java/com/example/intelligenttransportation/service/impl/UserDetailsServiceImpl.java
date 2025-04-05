//package com.example.intelligenttransportation.service.impl;
//
//import com.example.intelligenttransportation.entity.Role;
//import com.example.intelligenttransportation.entity.SysUser;
//import com.example.intelligenttransportation.mapper.PermissionMapper;
//import com.example.intelligenttransportation.mapper.RoleMapper;
//import com.example.intelligenttransportation.mapper.UserMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 自定义UserDetailsService实现
// * 用于Spring Security用户认证
// */
//@Service("serviceUserDetailsService")
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private RoleMapper roleMapper;
//
//    @Autowired
//    private PermissionMapper permissionMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 根据用户名查询用户
//        SysUser user = userMapper.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("用户名不存在: " + username);
//        }
//
//        // 用户被禁用
//        if (user.getStatus() != null && user.getStatus() != 1) {
//            throw new UsernameNotFoundException("用户已被禁用: " + username);
//        }
//
//        // 获取用户角色
//        List<Role> roles = roleMapper.findRolesByUserId(user.getId());
//        List<String> roleIds = roles.stream()
//                .map(role -> role.getId().toString())
//                .collect(Collectors.toList());
//
//        // 获取用户权限
//        List<String> permissions = new ArrayList<>();
//        // 如果有角色
//        if (!roleIds.isEmpty()) {
//            for (Role role : roles) {
//                // 获取角色对应的权限代码
//                List<String> permissionCodes = permissionMapper.selectPermissionCodesByRoleId(role.getId());
//                permissions.addAll(permissionCodes);
//            }
//        }
//
//        // 创建权限列表
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        // 添加角色，角色需要添加前缀"ROLE_"
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleCode()));
//        }
//        // 添加权限
//        for (String permission : permissions) {
//            authorities.add(new SimpleGrantedAuthority(permission));
//        }
//
//        // 返回UserDetails对象
//        return new User(user.getUsername(), user.getPassword(), authorities);
//    }
//}