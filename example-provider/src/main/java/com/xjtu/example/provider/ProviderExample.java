package com.xjtu.example.provider;

import com.xjtu.example.common.service.UserService;
import com.xjtu.yangrpc.RpcApplication;
import com.xjtu.yangrpc.config.RegistryConfig;
import com.xjtu.yangrpc.config.RpcConfig;
import com.xjtu.yangrpc.model.ServiceMetaInfo;
import com.xjtu.yangrpc.registry.Registry;
import com.xjtu.yangrpc.registry.RegistryFactory;
import com.xjtu.yangrpc.server.HttpServer;
import com.xjtu.yangrpc.server.VertxHttpServer;
import com.xjtu.yangrpc.registry.LocalRegistry;

/**
 * 服务 提供者 示例
 */
public class ProviderExample {
    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
