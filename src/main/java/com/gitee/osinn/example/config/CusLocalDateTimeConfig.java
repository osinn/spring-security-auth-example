package com.gitee.osinn.example.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义 LocalDateTime 序列化
 *
 * @author wency_cai
 */
@Configuration
public class CusLocalDateTimeConfig {

    static final String PATTERN_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    static final String UTC_TAIL = "Z";

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        builder.deserializerByType(LocalDateTime.class, CusLocalDateTimeDeserializer.CUS_INSTANCE);
        return builder.createXmlMapper(false).build();
    }

    /**
     * 继承默认的 LocalDateTimeDeserializer 重写反序列方法
     */
    public static class CusLocalDateTimeDeserializer extends LocalDateTimeDeserializer {

        public static final CusLocalDateTimeDeserializer CUS_INSTANCE = new CusLocalDateTimeDeserializer(DateTimeFormatter.ofPattern(PATTERN_UTC));
        private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        public CusLocalDateTimeDeserializer(DateTimeFormatter formatter) {
            super(formatter);
        }

        @Override
        public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            String timeStr = parser.getText();
            if (timeStr != null && !"".equals(timeStr)) {
                return convert(timeStr);
            }
            return super.deserialize(parser, context);
        }

        public LocalDateTime convert(String source) {
            source = source.trim();
            if ("".equals(source)) {
                return null;
            }
            if (source.matches("^\\d{4}-\\d{1,2}$")) {
                // yyyy-MM
                return LocalDateTime.parse(source + "-01 00:00:00", dateTimeFormatter);
            } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
                // yyyy-MM-dd
                return LocalDateTime.parse(source + " 00:00:00", dateTimeFormatter);
            } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
                // yyyy-MM-dd HH:mm
                return LocalDateTime.parse(source + ":00", dateTimeFormatter);
            } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
                // yyyy-MM-dd HH:mm:ss
                return LocalDateTime.parse(source, dateTimeFormatter);
            } else {
                throw new IllegalArgumentException("Invalid datetime value '" + source + "'");
            }
        }
    }
}