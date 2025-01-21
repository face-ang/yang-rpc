package com.xjtu.example.provider;

import com.xjtu.example.common.model.User;
import com.xjtu.example.common.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
