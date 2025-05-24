package io.github.osinn.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.osinn.example.entity.UserEntity;
import io.github.osinn.example.mapper.UserMapper;
import io.github.osinn.example.service.IUserService;
import org.springframework.stereotype.Service;


/**
 * 描述
 *
 * @author wency_cai
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

}
