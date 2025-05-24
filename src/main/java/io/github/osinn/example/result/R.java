package io.github.osinn.example.result;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一API响应结果封装
 *
 * @author wency_cai
 */
@Data
@NoArgsConstructor
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;
    /**
     * 承载数据
     */
    private T data;
    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回异常信息
     */
    private String error;

    private R(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    private R(IResultCode resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }

    private R(IResultCode resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private R(IResultCode resultCode, String message) {
        this(resultCode, null, message);
    }

    private R(IResultCode resultCode, T data, String message) {
        this(resultCode.getCode(), data, message);
    }

    public static <T> R<T> success() {
        return new R<>(ResultCode.SUCCESS);
    }

    public static <T> R<T> success(T data) {
        return new R<>(ResultCode.SUCCESS, data);
    }

    public static <T> R<T> success(T data, String message) {
        return new R<>(ResultCode.SUCCESS, data, message);
    }

    public static <T> R<T> fail(String message) {
        return new R<>(ResultCode.FAIL, message);
    }

    public static R<String> exError(ResultCode resultCode, String exMessage) {
        R<String> r = new R<>();
        r.setError(exMessage);
        r.setMessage(resultCode.getMessage());
        r.setCode(resultCode.getCode());
        return r;
    }

    public static R<String> exError(String message) {
        R<String> r = new R<>();
        r.setError(message);
        r.setMessage(ResultCode.INTERNAL_SERVER_ERROR.getMessage());
        r.setCode(ResultCode.INTERNAL_SERVER_ERROR.getCode());
        return r;
    }

    public static <T> R<T> result(IResultCode resultCode) {
        return new R<>(resultCode);
    }

    public static <T> R<T> result(IResultCode resultCode, T data) {
        return new R<>(resultCode, data);
    }

    public static <T> R<T> result(IResultCode resultCode, T data, String message) {
        return new R<>(resultCode, data, message);
    }

    public static <T> R<T> result(int code, String message) {
        return new R<>(code, null, message);
    }

    public static <T> R<T> result(int code, T data, String message) {
        return new R<>(code, data, message);
    }

}
