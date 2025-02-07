package com.xjtu.examplespringbootconsumer;

import com.xjtu.example.common.model.User;
import com.xjtu.example.common.service.UserService;
import com.xjtu.yangrpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl {
    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("DalDal");
        User resultUser = userService.getUser(user);
        System.out.println("消费者得到消息: " + resultUser.getName());
    }
}
