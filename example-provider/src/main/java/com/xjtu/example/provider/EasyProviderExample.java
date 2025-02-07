package com.xjtu.example.provider;

import com.xjtu.example.common.service.UserService;
import com.xjtu.yangrpc.registry.LocalRegistry;
import com.xjtu.yangrpc.server.HttpServer;
import com.xjtu.yangrpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {

    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        System.out.println("服务key: " + UserService.class.getName());
        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}