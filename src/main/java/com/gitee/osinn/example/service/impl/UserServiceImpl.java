package com.gitee.osinn.example.service.impl;

import com.gitee.osinn.boot.securityjwt.annotation.API;
import com.gitee.osinn.boot.securityjwt.annotation.APIMethodPermission;
import com.gitee.osinn.example.service.IUserService;
import com.gitee.osinn.example.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.gitee.osinn.example.service.impl.UserServiceImpl.SERVICE_NAME;

/**
 * 描述
 *
 * @author wency_cai
 */
@API(service = SERVICE_NAME)
@Service(SERVICE_NAME)
public class UserServiceImpl implements IUserService {

    public static final String SERVICE_NAME = "userService";

    @Override
    @APIMethodPermission(needLogin = true, permission = "test:111", needPermission = true)
    public List<UserVO> getUserAll() {
        List<UserVO> userVOList = new ArrayList<>();
        UserVO userVO = new UserVO();
        userVO.setId(1);
        userVO.setName("测试");
        userVOList.add(userVO);
        return userVOList;
    }

    @Override
    public UserVO getUser(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<UserVO> pageUser(Map<String, Object> params) {
        return null;
    }

    @Override
    @APIMethodPermission(needLogin = true, permission = "test:11112", needPermission = true)
    public List<UserVO> testPermission() {
        List<UserVO> userVOList = new ArrayList<>();
        UserVO userVO = new UserVO();
        userVO.setId(1);
        userVO.setName("测试");
        userVOList.add(userVO);
        return userVOList;
    }

}
