package com.gitee.osinn.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.gitee.osinn.boot.securityjwt.annotation.API;
import com.gitee.osinn.boot.securityjwt.annotation.APIMethodPermission;
import com.gitee.osinn.boot.securityjwt.security.dto.AuthUser;
import com.gitee.osinn.boot.securityjwt.security.dto.CaptchaCodeDTO;
import com.gitee.osinn.boot.securityjwt.security.dto.JwtUser;
import com.gitee.osinn.boot.securityjwt.service.IOnlineUserService;
import com.gitee.osinn.boot.securityjwt.service.ISecurityCaptchaCodeService;
import com.gitee.osinn.example.service.IMyTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.gitee.osinn.example.service.impl.MyTestServiceImpl.SERVICE_NAME;

/**
 * 自定义图形验证码
 */
@API(service = SERVICE_NAME)
@Service(SERVICE_NAME)
public class MyTestServiceImpl implements IMyTestService {

    public static final String SERVICE_NAME = "myTestService";

    @Autowired
    private ISecurityCaptchaCodeService securityCaptchaCodeService;

    @Autowired
    private IOnlineUserService onlineUserService;

    @Override
    @APIMethodPermission
    public JwtUser customAuth(Map<String, Object> params) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        AuthUser authUser = JSON.parseObject(JSON.toJSONString(params), AuthUser.class);
        JwtUser jwtUser = onlineUserService.customAuth(authUser, request);
        return jwtUser;
    }

    @Override
    @APIMethodPermission
    public CaptchaCodeDTO createCaptchaCode() {
        // 演示还是使用内置逻辑生成图形验证码
        CaptchaCodeDTO captchaCode = securityCaptchaCodeService.createCaptchaCode();
        return captchaCode;
    }

}
