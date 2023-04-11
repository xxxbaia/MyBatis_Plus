package com.example.mybatis_plus.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis_plus.bean.User;
import com.example.mybatis_plus.mapper.UserMapper;
import com.example.mybatis_plus.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @auther BaiYiLei
 * @Date 2023/4/11
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper,User> implements UserService {
}
