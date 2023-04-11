package com.example.mybatis_plus.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @auther BaiYiLei
 * @Date 2023/4/11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User> {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_UUID)
    private Long id;
    @TableField(condition = SqlCondition.LIKE)
    private String name;
    //@TableField(condition = "%s&lt;#{%s}",value = "age") 通过对象实体进行crud时可以在字段上添加过滤条件
    private Integer age;
    private String email;
    @TableField("manager_id")
    private Long managerId;
    @TableField("create_time")
    private String createTime;
    private String updateTime;
    private String version;
    private String deleted;
}
