package com.gitee.osinn.example.service;

import com.gitee.osinn.example.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author wency_cai
 */
public interface IUserService {

    UserVO getUser(Map<String, Object> params);

    List<UserVO> pageUser(Map<String, Object> params);

    List<UserVO> testPermission();

    List<UserVO> getUserAll();

}
