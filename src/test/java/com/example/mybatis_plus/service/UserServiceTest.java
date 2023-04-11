package com.example.mybatis_plus.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatis_plus.bean.User;
import com.example.mybatis_plus.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @auther BaiYiLei
 * @Date 2023/4/11
 */
@SpringBootTest
@Slf4j
public class UserServiceTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void test1(){
        List<User> users = userMapper.selectList(null);
        users.stream().forEach(System.out::println);
    }
    @Test
    public void insert1(){
        User user = new User();
        user.setName("向东");
        user.setAge(25);
        user.setCreateTime(LocalDateTime.now().toString());
        user.setEmail("xiangdong@163.com");
        user.setId(1088248166370833567L);
        int rows = userMapper.insert(user);
        System.out.println(rows);
    }
    @Test
    public void update1(){
        User user = new User();
        user.setId(1088248166370832384L);
        user.setEmail("zhangsan@163.com");
        userMapper.updateById(user);
        userMapper.selectList(null).stream().forEach(System.out::println);
    }
    @Test
    public void select1(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("name","三");
        userMapper.selectList(userQueryWrapper).stream().forEach(System.out::println);
    }

    /**
     * 多条件查询
     */
    @Test
    public void select2(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("name","%雨%").lt("age",40);
        userMapper.selectList(userQueryWrapper).stream().forEach(System.out::println);
    }
    @Test
    public void select3(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("name","%雨%").between("age",20,40).isNotNull("email");
        userMapper.selectList(userQueryWrapper).stream().forEach(System.out::println);
    }

    /**
     * 多字段排序
     */
    @Test
    public void select4(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("name","王%").or().ge("age",25).orderByDesc("age").orderByAsc("id");
        userMapper.selectList(userQueryWrapper).stream().forEach(System.out::println);
    }

    /**
     * 日期作为查询条件
     */
    @Test
    public void select5(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}","2019-02-14")
                .inSql("manager_id","select id from tbl_user where name like '王%'");
        userMapper.selectList(userQueryWrapper).stream().forEach(System.out::println);
    }
    @Test
    public void select6(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.likeRight("name","王").and(qw -> qw.lt("age",40).or().isNotNull("email"));
        userMapper.selectList(userQueryWrapper).stream().forEach(System.out::println);
    }
    @Test
    public void select7(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.likeRight("name","王").or(qw -> qw.lt("age",40).gt("age",20).isNotNull("email"));
        userMapper.selectList(userQueryWrapper).stream().forEach(System.out::println);
    }

    /**
     * 先查符合括号中条件的用户
     */
    @Test
    public void select8(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.nested(qw -> qw.lt("age",40).or().isNotNull("email")).likeRight("name","王");
        userMapper.selectList(userQueryWrapper).stream().forEach(System.out::println);
    }

    /**
     * 查年龄在指定区间的
     */
    @Test
    public void select9(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.in("age", Arrays.array(30,31,34,35));
        userMapper.selectList(userQueryWrapper).stream().forEach(System.out::println);
    }

    /**
     * 只显示一条数据，该方法有sql注入的风险
     */
    @Test
    public void select10(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.last("limit 1");
        userMapper.selectList(userQueryWrapper).stream().forEach(System.out::println);
    }

    /**
     * 只查询需要的字段
     */
    @Test
    public void select11(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.select("id","name").like("name","雨").lt("age",40);
        userMapper.selectList(userQueryWrapper).stream().forEach(System.out::println);
    }

    /**
     * 排除不需要的字段
     */
    @Test
    public void select12(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.select(User.class,qw -> !qw.getColumn().equals("create_time")&&!qw.getColumn().equals("email")).like("name","雨").lt("age",40);
        userMapper.selectList(userQueryWrapper).stream().forEach(System.out::println);
    }

    /**
     * 使用condition
     */
    @Test
    public void select13(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.select(User.class,qw -> !qw.getColumn().equals("create_time")&&!qw.getColumn().equals("email")).like("name","雨").lt("age",40);
        userMapper.selectList(userQueryWrapper).stream().forEach(System.out::println);
    }

    /**
     * 对象实体作为条件查询的参数
     * 查询条件默认是等于，可在实体类对应字段上添加条件
     */
    @Test
    public void select14(){
        User user = new User();
        user.setName("雨");
        user.setAge(35);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(user);
        userMapper.selectList(userQueryWrapper).stream().forEach(System.out::println);
    }

    /**
     * allEq
     */
    @Test
    public void select15(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        HashMap<String, Object> map1 = new HashMap<>();
        HashMap<String, Object> map2 = new HashMap<>();
        map1.put("name","张三");
        map1.put("age",null);
        map2.put("name","张三");
        map2.put("age",20);
        map2.put("email","xxxxx");
        //map2.put("create_time","2023-04-11");
        userQueryWrapper.allEq((k,v) -> !k.equals("email") && v != null,map2);
        userMapper.selectList(userQueryWrapper).forEach(System.out::println);
    }
    /**
     * 使用lambda表达式构建QueryWrapper
     * 优点是防止字段名写错
     */
    @Test
    public void lambdaSelect(){
        //lambdaQueryWrapper三种创建方式
        //LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();

        lambdaQueryWrapper.select(User::getId,User::getName,User::getAge).lt(User::getAge,35);
        userMapper.selectMaps(lambdaQueryWrapper).forEach(System.out::println);
    }
}