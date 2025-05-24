package io.github.osinn.example.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回码
 *
 * @author wency_cai
 */
@Getter
@AllArgsConstructor
public enum ResultCode implements IResultCode {

    /**
     * 操作成功
     */
    SUCCESS(20000, "操作成功"),

    FAIL(30000, "失败"),
    /**
     * 业务异常
     */
    INTERNAL_SERVER_ERROR(50000, "服务器异常，请联系管理员"),

    /**
     * 请求未授权
     */
    UN_AUTHORIZED(40001, "请求未授权，请联系管理员"),

    /**
     * 404 没找到请求
     */
    NOT_FOUND(40004, "404 没找到请求"),

    /**
     * 消息不能读取
     */
    MSG_NOT_READABLE(40000, "消息不能读取"),

    /**
     * 不支持当前请求方法
     */
    METHOD_NOT_SUPPORTED(40005, "不支持当前请求方法"),

    /**
     * 不支持当前媒体类型
     */
    MEDIA_TYPE_NOT_SUPPORTED(41500, "不支持当前媒体类型"),

    /**
     * 请求被拒绝
     */
    REQ_REJECT(40300, "请求被拒绝"),

    /**
     * 缺少必要的请求参数
     */
    PARAM_MISS(40100, "缺少必要的请求参数"),

    /**
     * 参数校验失败
     */
    PARAM_VALID_FAIL(40200, "参数校验失败"),

    /**
     * 登录超时,请重新登录
     */
    TOKEN_ERROR(40800, "登录超时,请重新登录");

    /**
     * code编码
     */
    private final int code;

    /**
     * 中文信息描述
     */
    private final String message;
}
