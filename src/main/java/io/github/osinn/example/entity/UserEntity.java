package io.github.osinn.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 描述
 *
 * @author wency_cai
 */
@Data
@TableName("sys_user")
public class UserEntity {

    @TableId(value = "id",  type = IdType.AUTO)
    private Integer id;

    private String account;

    private String userName;

    private String password;

}
