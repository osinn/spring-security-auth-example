package com.gitee.osinn.example.service.impl;

import io.github.osinn.securitytoken.security.dto.JwtRoleInfo;
import io.github.osinn.securitytoken.security.dto.JwtUser;
import io.github.osinn.securitytoken.security.dto.OnlineUser;
import io.github.osinn.securitytoken.security.dto.ResourcePermission;
import io.github.osinn.securitytoken.service.ISecurityService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 不是api服务，外部访问不了类接口
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
        jwtUser.setAccount("admin");

        return jwtUser;
    }

    /**
     * 获取用户的角色以及权限
     *
     * @param userId
     * @return
     */
    @Override
    public JwtRoleInfo fetchRolePermissionInfo(Object userId) {
        JwtRoleInfo jwtRoleInfo = new JwtRoleInfo();
        JwtRoleInfo.BaseRoleInfo baseRoleInfo = new JwtRoleInfo.BaseRoleInfo();
        baseRoleInfo.setRoleCode("test");
        baseRoleInfo.setId(1);
        baseRoleInfo.setName("demo");
        jwtRoleInfo.setPermissions(Lists.newArrayList("test:111"));
        jwtRoleInfo.setRoles(Lists.newArrayList(baseRoleInfo));
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
