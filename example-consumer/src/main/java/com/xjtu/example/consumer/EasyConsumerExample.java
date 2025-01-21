package com.xjtu.example.consumer;

import com.xjtu.example.common.model.User;
import com.xjtu.example.common.service.UserService;
import com.xjtu.yangrpc.proxy.ServiceProxyFactory;

/**
 * 简易服务消费者示例
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
        // 静态代理
//        UserService userService = new UserServiceProxy();
        // 动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("wydal");
        // 调用(调用 userService 时，实际是调用了代理对象的 invoke 方法，并且获取到了 serviceName、methodName、参数类型和列表等信息。)
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
