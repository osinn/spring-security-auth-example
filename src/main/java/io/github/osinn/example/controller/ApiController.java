package io.github.osinn.example.controller;

import io.github.osinn.example.entity.UserEntity;
import io.github.osinn.example.result.R;
import io.github.osinn.example.service.IUserService;
import io.github.osinn.security.annotation.AuthIgnore;
import io.github.osinn.security.constants.AuthConstant;
import io.github.osinn.security.security.dto.AuthLoginParam;
import io.github.osinn.security.security.dto.AuthUserInfo;
import io.github.osinn.security.security.dto.OnlineUser;
import io.github.osinn.security.service.IOnlineUserService;
import io.github.osinn.security.utils.TokenUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author wency_cai
 */
@Slf4j
@RestController
public class ApiController {

    @Resource
    private IOnlineUserService onlineUserService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private IUserService userService;

    @AuthIgnore
    @PostMapping("/addUser")
    public R<String> addUser(@RequestBody UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userService.save(userEntity);
        return R.success();
    }


    @PostMapping("/login")
    public AuthUserInfo login(@RequestBody AuthLoginParam authLoginParam, HttpServletRequest request, HttpServletResponse response) {
        AuthUserInfo authUserInfo = onlineUserService.auth(authLoginParam, request, response);
//        AuthUserInfo authUserInfo = onlineUserService.customAuth(authLoginParam, request);
        return authUserInfo;
    }

    @GetMapping("/getUserInfo")
    @PreAuthorize("hasAnyAuthority('system:dept:list')")
    public R<OnlineUser> getUserInfo() {
        return R.success(TokenUtils.getOnlineUserInfo());
    }

    @GetMapping("/getUserInfo2")
    @PreAuthorize("hasAnyAuthority('sys:user:list2')")
    public R<OnlineUser> getUserInfo2() {
        return R.success(TokenUtils.getOnlineUserInfo());
    }

    @GetMapping("/getUserList")
    @PreAuthorize("@pms.hasPermission('sys:user:list:test')")
    public OnlineUser getUserList() {
        return TokenUtils.getOnlineUserInfo();
    }


    @GetMapping("/getUserList2")
    @PreAuthorize("@pms.hasPermission('sys:user:list')")
    public OnlineUser getUserList2() {
        OnlineUser onlineUser = TokenUtils.getOnlineUserInfo();
        return onlineUser;
    }

    @GetMapping("/testNotLogin")
    public OnlineUser testNotLogin() {
        OnlineUser onlineUser = TokenUtils.getOnlineUserInfo();
        return onlineUser;
    }

    @GetMapping("/testNotLogin2")
    public String testNotLogin2() {
        return "OK";
    }

    @GetMapping("/deleteUser")
    @PreAuthorize("@pms.hasPermission('sys:user:delete')")
    public String deleteUser() {
        return "删除用户";
    }

    @GetMapping("/deleteUser2")
    @PreAuthorize("hasAnyAuthority('sys:user:delete')")
    public String deleteUser2() {
        return "删除用户";
    }

    /**
     * 刷新用户权限
     *
     * @return
     */
    @GetMapping("/refreshUserPermission")
    public String refreshUserPermission() {
        OnlineUser onlineUser = TokenUtils.getOnlineUserInfo();
        onlineUserService.refreshUserPermission(onlineUser.getId());
        return "OK";
    }

    /**
     * 使用户下线
     *
     * @return
     */
    @GetMapping("/editUserInfoForciblyLogout")
    public String editUserInfoForciblyLogout(@RequestParam("userId") Long userId) {
        onlineUserService.logoutForcibly(List.of(userId));
        return "OK";
    }

    /**
     * 清理缓存
     *
     * @return
     */
    @GetMapping("/deleteCache")
    public String deleteCache() {
        onlineUserService.deleteCacheByPrefix(AuthConstant.SYS_RESOURCE_PERMISSION_ALL_CACHE_KEY);
        return "OK";
    }
}
