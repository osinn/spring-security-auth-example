package com.gitee.osinn.example.controller;

import com.gitee.osinn.boot.securityjwt.constants.JwtConstant;
import com.gitee.osinn.boot.securityjwt.security.dto.JwtUser;
import com.gitee.osinn.boot.securityjwt.security.dto.OnlineUser;
import com.gitee.osinn.boot.securityjwt.service.IOnlineUserService;
import com.gitee.osinn.boot.securityjwt.service.ISecurityService;
import com.gitee.osinn.boot.securityjwt.utils.DesEncryptUtils;
import com.gitee.osinn.boot.securityjwt.utils.RedisUtils;
import com.gitee.osinn.boot.securityjwt.utils.SpringContextHolder;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 描述
 *
 * @author wency_cai
 */
@RestController
public class ApiController {

    @Data
    public static class Params {

        /**
         * 服务名称
         */
        private String service;

        /**
         * 业务方法名称
         */
        private String methodName;

        /**
         * 参数
         */
        private Map<String, Object> params;

        private String token;
    }

    @Data
    public static class LoginParams {

        /**
         * 登录账号
         */
        private String account;

        /**
         * 密码
         */
        private String password;
    }

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private IOnlineUserService onlineUserService;

    /**
     * 统一API入口
     *
     * @param param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object invokeApi(@RequestBody @Validated Params param) throws Exception {
        Object service = SpringContextHolder.getBean(param.getService());
        Method method;
        if (param.getParams() == null) {
            method = service.getClass().getMethod(param.getMethodName());
            return method.invoke(service);
        } else {
            // 每个接口的参数必须是Map
            method = service.getClass().getMethod(param.getMethodName(), Map.class);
            return method.invoke(service, param.getParams());
        }
    }

    /**
     * 统一API入口
     *
     * @param param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/api/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody @Validated LoginParams param, HttpServletRequest request) throws Exception {
        JwtUser jwtUser = onlineUserService.customAuth(param, request);
        return jwtUser.getToken();
    }

    /**
     * 统一API入口
     *
     * @param param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/api/ping", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String ping(@RequestBody @Validated Params param) throws Exception {
        return "OK";
    }
    /**
     * 统一API入口
     *
     * @param param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/api/getTokenExpire", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getTokenExpire(@RequestBody @Validated Params param) throws Exception {
        long expire = redisUtils.getExpire(JwtConstant.ONLINE_USER_INFO_KEY_PREFIX + DesEncryptUtils.md5DigestAsHex(param.getToken()));
        return expire;
    }

}
