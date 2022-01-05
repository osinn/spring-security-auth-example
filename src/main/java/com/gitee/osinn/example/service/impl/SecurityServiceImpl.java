package com.gitee.osinn.example.service.impl;

import com.gitee.osinn.boot.securityjwt.security.dto.JwtRoleInfo;
import com.gitee.osinn.boot.securityjwt.security.dto.JwtUser;
import com.gitee.osinn.boot.securityjwt.security.dto.OnlineUser;
import com.gitee.osinn.boot.securityjwt.security.dto.ResourcePermission;
import com.gitee.osinn.boot.securityjwt.service.ISecurityService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 此项目只是做为 @API 使用演示，不需要登录认证，权限认证
 *
 * @author wency_cai
 */
@Service
public class SecurityServiceImpl implements ISecurityService {

    /**
     * 如果是微信授权登录可以使用自定义登录接口 返回JwtUser 信息即可
     *
     * @param principal
     * @return
     */
    @Override
    public JwtUser customAuth(Object principal) {
        JwtUser jwtUser = new JwtUser();
        jwtUser.setId(1401043674048851970L);
        jwtUser.setNickname("超级管理员");
        jwtUser.setRoles(Lists.newArrayList());
        jwtUser.setAuthorities(Lists.newArrayList());
        jwtUser.setAccount("admin");
        return jwtUser;
    }

    /**
     * 比如移动端不需要权限控制直接返回空
     *
     * @param userId
     * @return
     */
    @Override
    public JwtRoleInfo fetchRolePermissionInfo(Object userId) {
        JwtRoleInfo jwtRoleInfo = new JwtRoleInfo();
        jwtRoleInfo.setPermissions(Lists.newArrayList());
        jwtRoleInfo.setRoles(Lists.newArrayList());
        return jwtRoleInfo;
    }

    @Override
    public List<ResourcePermission> fetchResourcePermissionAll() {
        return Lists.newArrayList();
    }

    @Override
    public Object loadUserByUsername(String account) {
        return null;
    }

    @Override
    public String getCustomizeToken(JwtUser jwtUser) {
        return null;
    }

    @Override
    public void logoutBeforeHandler(HttpServletRequest request, HttpServletResponse response, OnlineUser loginUser) {

    }
}
