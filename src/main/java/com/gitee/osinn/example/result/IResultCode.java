package com.gitee.osinn.example.result;

import java.io.Serializable;

/**
 * 返回码
 *
 * @author wency_cai
 */
public interface IResultCode extends Serializable {

    /**
     * 消息
     *
     * @return String
     */
    String getMessage();

    /**
     * 状态码
     *
     * @return int
     */
    int getCode();

}
