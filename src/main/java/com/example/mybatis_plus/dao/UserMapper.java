package com.example.mybatis_plus.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis_plus.bean.User;
import org.apache.ibatis.annotations.Param;

/**
 * @auther BaiYiLei
 * @Date 2023/4/11
 */
public interface UserMapper extends BaseMapper<User> {
    IPage<User> selectUserPage(Page<User> page, @Param(Constants.WRAPPER)Wrapper<User> wrapper);
}
