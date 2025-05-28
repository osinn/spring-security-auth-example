package io.github.osinn.example.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.osinn.example.entity.UserEntity;
import io.github.osinn.example.service.IUserService;
import io.github.osinn.security.enums.AuthHttpStatus;
import io.github.osinn.security.exception.SecurityAuthException;
import io.github.osinn.security.security.dto.*;
import io.github.osinn.security.service.ISecurityService;
import io.github.osinn.security.starter.SecurityProperties;
import io.github.osinn.security.utils.CryptoUtils;
import io.github.osinn.security.utils.StrUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 示例
 *
 * @author wency_cai
 */
@Slf4j
@Service
public class SecurityServiceImpl implements ISecurityService {

    @Resource
    private IUserService userService;

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private PasswordEncoder passwordEncoder;

//    /**
//     * 自定义登录
//     * 如果是微信授权登录可以使用自定义登录接口 返回AuthUserInfo 信息即可
//     *
//     * @param principal 登录接口登录信息对象，由开发者自己定义传入
//     * @return
//     */
//    @Override
//    public AuthUserInfo customAuth(Object principal) {
//        AuthLoginParam authLoginParam = (AuthLoginParam) principal;
//
//        UserEntity userEntity = userService.getOne(Wrappers.lambdaQuery(UserEntity.class)
//                .eq(UserEntity::getAccount, authLoginParam.getAccount())
//        );
//
//        String password;
//        if (!StrUtils.isEmpty(securityProperties.getRsaPrivateKey())) {
//            try {
//                password = CryptoUtils.rsaDecrypt(authLoginParam.getPassword(), securityProperties.getRsaPrivateKey());
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//                throw new SecurityAuthException(AuthHttpStatus.PASSWORD_ERROR.getCode(), AuthHttpStatus.TOKEN_UNAUTHORIZED.getMessage());
//            }
//        } else {
//            password = authLoginParam.getPassword();
//        }
//
//        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
//            throw new BadCredentialsException(AuthHttpStatus.TOKEN_UNAUTHORIZED.getMessage());
//        }
//
//        AuthUserInfo authUserInfo = new AuthUserInfo();
//        authUserInfo.setId(userEntity.getId());
//        authUserInfo.setNickname(userEntity.getUserName());
//        authUserInfo.setAccount(userEntity.getAccount());
//
//        return authUserInfo;
//    }

    /**
     * 根据账号获取用户信息
     *
     * @param account
     * @return AuthUserInfo
     */
    @Override
    public AuthUserInfo loadUserByUsername(String account) {
        UserEntity userEntity = userService.getOne(Wrappers.lambdaQuery(UserEntity.class)
                .eq(UserEntity::getAccount, account)
        );
        if (userEntity == null) {
            return null;
        }

        return new AuthUserInfo(
                userEntity.getId(),
                userEntity.getAccount(),
                userEntity.getUserName(),
                userEntity.getPassword(),
                null, // 如果需要额外扩展用户信息，请自赋值一个对象即可, 从在线用户信息中获取 -> onlineUser.getExtendField() 即为此处传入的对象
                null, // 不需要赋值，内置会自行处理
                null, // 不需要赋值，内置会自行处理
                null, // 不需要赋值，内置会自行处理
                null // 不需要赋值，内置会自行处理
        );
    }

    /**
     * 获取用户的角色以及权限
     *
     * @param userId
     * @return
     */
    @Override
    public AuthRoleInfo getRolePermissionInfo(Object userId) {
        AuthRoleInfo jwtRoleInfo = new AuthRoleInfo();

        List<ResourcePermission> resourcePermissionList = new ArrayList<>();
        resourcePermissionList.add(new ResourcePermission(null, "sys:user:list"));

        AuthRoleInfo.BaseRoleInfo roleInfo = new AuthRoleInfo.BaseRoleInfo(1, "demo", "test", null, resourcePermissionList);

        jwtRoleInfo.setRoles(List.of(roleInfo));
        return jwtRoleInfo;
    }

    /**
     * 获取系统所有资源权限
     *
     * @return
     */
    @Override
    public List<ResourcePermission> getSysResourcePermissionAll() {
        List<ResourcePermission> resourcePermissionList = new ArrayList<>();
        resourcePermissionList.add(new ResourcePermission(null, "sys:user:list"));
        resourcePermissionList.add(new ResourcePermission(null, "sys:user:list2"));
        return resourcePermissionList;
    }

}
