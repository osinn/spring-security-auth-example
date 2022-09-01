package com.gitee.osinn.example.service;

import io.github.osinn.securitytoken.security.dto.CaptchaCodeDTO;
import io.github.osinn.securitytoken.security.dto.JwtUser;

import java.util.Map;

public interface IMyTestService {

    JwtUser customAuth(Map<String, Object> params);

    /**
     * 创建图形验证码
     *
     * @return 返回base64 图形验证码对象
     */
    CaptchaCodeDTO createCaptchaCode();

}
