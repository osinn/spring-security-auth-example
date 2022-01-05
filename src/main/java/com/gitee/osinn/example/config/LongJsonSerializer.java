package com.gitee.osinn.example.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


/**
 * Long类型 序列化输出
 *
 * @author wency_cai
 */
@Slf4j
public class LongJsonSerializer extends JsonSerializer<Long> {

    /**
     * 前端最大支持数字类型长度
     */
    private final static Long JS_NUMB_MAX = 9007199254740992L;

    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (value != null) {
            if (value > JS_NUMB_MAX) {
                log.warn("------>  值为：{} Long 类型，数值过大，避免前端接收丢失精度，将转成字符串输出  <------", value);
                jsonGenerator.writeString(String.valueOf(value));
            } else {
                jsonGenerator.writeNumber(value);
            }

        }
    }

}