package com.gitee.osinn.example.service.impl;

import com.alibaba.fastjson.JSON;
import io.github.osinn.securitytoken.annotation.API;
import io.github.osinn.securitytoken.annotation.APIMethodPermission;
import io.github.osinn.securitytoken.security.dto.AuthUser;
import io.github.osinn.securitytoken.security.dto.CaptchaCodeDTO;
import io.github.osinn.securitytoken.security.dto.JwtUser;
import io.github.osinn.securitytoken.service.IOnlineUserService;
import io.github.osinn.securitytoken.service.ISecurityCaptchaCodeService;
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
