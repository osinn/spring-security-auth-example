package com.gitee.osinn.example.exception;

import com.gitee.osinn.boot.securityjwt.exception.SecurityJwtException;
import com.gitee.osinn.example.result.R;
import com.gitee.osinn.example.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 全局异常
 *
 * @author wency_cai
 */
@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * @param e
     * @return
     * @throws Exception
     * @// TODO: 2018/4/25 处理token 过期异常
     */
    @ExceptionHandler(value = DisabledException.class)
    public ResponseEntity<R> disabledException(DisabledException e) throws Exception {
        log.error(e.getMessage(), e);
        if(e.getCause() instanceof SecurityJwtException) {
            SecurityJwtException securityJwtException = (SecurityJwtException)e.getCause();
            return this.securityJwtException(securityJwtException);
        }
        return buildResponseEntity(R.result(HttpStatus.UNAUTHORIZED.value(), "用户已被禁用"));
    }

    /**
     * @param e
     * @return
     * @throws Exception
     * @// TODO: 2018/4/25 处理token 过期异常
     */
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<R> usernameNotFoundException(UsernameNotFoundException e) throws Exception {
        log.error(e.getMessage(), e);
        if(e.getCause() instanceof SecurityJwtException) {
            SecurityJwtException securityJwtException = (SecurityJwtException)e.getCause();
            return this.securityJwtException(securityJwtException);
        }
        return buildResponseEntity(R.result(HttpStatus.UNAUTHORIZED.value(), "用户不存在"));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<R> AuthenticationExceptionHandler(AuthenticationException e) {
        log.error(e.getMessage(), e);
        if(e.getCause() instanceof SecurityJwtException) {
            SecurityJwtException securityJwtException = (SecurityJwtException)e.getCause();
            return this.securityJwtException(securityJwtException);
        }
        return buildResponseEntity(R.result(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
    }


    /**
     * @param e
     * @return
     * @throws Exception
     * @// TODO: 2018/4/25 方法访问权限不足异常
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<R> accessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        if(e.getCause() instanceof SecurityJwtException) {
            SecurityJwtException securityJwtException = (SecurityJwtException)e.getCause();
            return this.securityJwtException(securityJwtException);
        }
        return buildResponseEntity(R.result(HttpStatus.UNAUTHORIZED.value(), "权限不足"));
    }

    /**
     * BadCredentialsException
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<R> badCredentialsException(BadCredentialsException e) {
        // 打印堆栈信息
        String message = "坏的凭证".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
        log.error(message);
        if(e.getCause() instanceof SecurityJwtException) {
            SecurityJwtException securityJwtException = (SecurityJwtException)e.getCause();
            return this.securityJwtException(securityJwtException);
        }
        return buildResponseEntity(R.fail(message));
    }


    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = SecurityJwtException.class)
    public ResponseEntity<R> securityJwtException(SecurityJwtException e) {
        // 打印堆栈信息
        log.error(e.getMessage(), e);
        if (e.getStatus() == null) {
            return buildResponseEntity(R.fail(e.getMessage()));
        } else {
            return buildResponseEntity(R.result(e.getStatus(), e.getMessage()));
        }
    }


    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<R> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String msg = "不能为空";
        if (msg.equals(message)) {
            message = str[1] + ":" + message;
        }
        return buildResponseEntity(R.result(ResultCode.PARAM_VALID_FAIL.getCode(), message));
    }

    /**
     * 统一返回
     */
    private ResponseEntity<R> buildResponseEntity(R apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.OK);
    }

}
