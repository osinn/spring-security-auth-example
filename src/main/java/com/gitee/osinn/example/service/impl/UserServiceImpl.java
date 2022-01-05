package com.gitee.osinn.example.service.impl;

import com.gitee.osinn.boot.securityjwt.annotation.API;
import com.gitee.osinn.example.service.IUserService;
import com.gitee.osinn.example.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author wency_cai
 */
@API(service = "userService")
public class UserServiceImpl implements IUserService {

    @Override
    public UserVO getUser(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<UserVO> pageUser(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<UserVO> getUserAll() {
        return null;
    }

}
