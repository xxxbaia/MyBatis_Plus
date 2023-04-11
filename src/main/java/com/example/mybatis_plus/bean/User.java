package com.example.mybatis_plus.bean;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @auther BaiYiLei
 * @Date 2023/4/11
 */
@Data
@TableName("tbl_user")
public class User {
    private Long id;
    @TableField(condition = SqlCondition.LIKE)
    private String name;
    //@TableField(condition = "%s&lt;#{%s}",value = "age")
    private Integer age;
    private String email;
    @TableField("manager_id")
    private Long mId;
    @TableField("create_time")
    private String createTime;
    private String updateTime;
    private String version;
    private String deleted;
}
