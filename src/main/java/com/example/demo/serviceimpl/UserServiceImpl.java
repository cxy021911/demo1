package com.example.demo.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.Entity.user;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class

UserServiceImpl extends ServiceImpl<UserMapper, user> implements UserService {
    // MyBatis-Plus 自带：save新增、remove删除、update修改、get/list查询
}