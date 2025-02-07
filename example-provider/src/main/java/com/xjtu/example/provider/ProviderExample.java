package com.xjtu.example.provider;

import com.xjtu.example.common.service.UserService;
import com.xjtu.yangrpc.RpcApplication;
import com.xjtu.yangrpc.bootstrap.ProviderBootstrap;
import com.xjtu.yangrpc.config.RegistryConfig;
import com.xjtu.yangrpc.config.RpcConfig;
import com.xjtu.yangrpc.model.ServiceMetaInfo;
import com.xjtu.yangrpc.model.ServiceRegisterInfo;
import com.xjtu.yangrpc.registry.Registry;
import com.xjtu.yangrpc.registry.RegistryFactory;
import com.xjtu.yangrpc.server.HttpServer;
import com.xjtu.yangrpc.server.VertxHttpServer;
import com.xjtu.yangrpc.registry.LocalRegistry;
import com.xjtu.yangrpc.server.tcp.VertxTcpServer;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务 提供者 示例
 */
public class ProviderExample {
//    public static void main(String[] args) {
//        // RPC 框架初始化
//        RpcApplication.init();
//
//        // 注册服务
//        String serviceName = UserService.class.getName();
//        LocalRegistry.register(serviceName, UserServiceImpl.class);
//
//        // 注册服务到注册中心
//        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
//        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
//        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
//        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
//        serviceMetaInfo.setServiceName(serviceName);
//        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
//        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
//        try {
//            registry.register(serviceMetaInfo);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        // 启动 web 服务
////        HttpServer httpServer = new VertxHttpServer();
////        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
//        // 启动 TCP 服务
//        VertxTcpServer vertxTcpServer = new VertxTcpServer();
//        vertxTcpServer.doStart(8080);
//    }
    public static void main(String[] args) {
        // 要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
