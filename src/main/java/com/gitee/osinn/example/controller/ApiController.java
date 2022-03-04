package com.gitee.osinn.example.controller;

import com.alibaba.fastjson.JSON;
import com.gitee.osinn.boot.securityjwt.utils.SpringContextHolder;
import com.gitee.osinn.example.result.R;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 单独测试@API注解与@APIMethodPermission注解配合使用
 *
 * @author wency_cai
 */
@Slf4j
@RestController
public class ApiController {

    @Data
    public static class Params {

        /**
         * 服务名称
         */
        @NotBlank(message = "服务名称不能为空")
        private String service;

        /**
         * 业务方法名称
         */
        @NotBlank(message = "业务方法不能为空")
        private String methodName;

        /**
         * 参数
         */
        private Map<String, Object> params;

    }

    /**
     * 统一API入口
     *
     * @param param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object invokeApi(@RequestBody @Validated Params param) throws Exception {
        Object service = null;
        try {
            service = SpringContextHolder.getBean(param.getService());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (ObjectUtils.isEmpty(service)) {
            return R.fail("服务不存在");
        }

        log.info("请求服务：[{}]，服务接口：[{}]\n请求参数：[{}]",
                param.getService(),
                param.getMethodName(),
                param.getParams() == null ? "" : JSON.toJSONString(param.getParams()));

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

}
