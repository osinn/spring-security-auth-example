package com.gitee.osinn.example.vo;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述
 *
 * @author wency_cai
 */
@Data
public class UserVO {
    
    private Integer id;

    private String name;

    public static void main(String[] args) throws Exception {
        long expireTime = 14400L * 1000;
//        System.out.println(System.currentTimeMillis() - expireTime);
//        System.out.println((System.currentTimeMillis() - expireTime) > expireTime / 2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = simpleDateFormat.parse("2022-01-05 07:00:00");
        Date parse2 = simpleDateFormat.parse("2022-01-05 09:00:00");
        System.out.println(expireTime / 2);
        System.out.println(expireTime);
        System.out.println(parse2.getTime() - parse.getTime());
    }
}
