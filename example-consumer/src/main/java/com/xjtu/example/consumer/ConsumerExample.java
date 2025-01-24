package com.xjtu.example.consumer;


import com.xjtu.example.common.model.User;
import com.xjtu.example.common.service.UserService;
import com.xjtu.yangrpc.config.RpcConfig;
import com.xjtu.yangrpc.proxy.ServiceProxyFactory;
import com.xjtu.yangrpc.utils.ConfigUtils;

/**
 * 服务消费者示例
 */
public class ConsumerExample {
    public static void main(String[] args) {
        // 1. 测试配置文件读取
//        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
//        System.out.println(rpc);
        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("Dal");
        // 调用 getUser
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
        // 调用 getNumber
        long number = userService.getNumber();
        System.out.println(number);
    }
}
