package com.xjtu.yangrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import com.xjtu.yangrpc.RpcApplication;
import com.xjtu.yangrpc.config.RpcConfig;
import com.xjtu.yangrpc.constant.RpcConstant;
import com.xjtu.yangrpc.fault.retry.RetryStrategy;
import com.xjtu.yangrpc.fault.retry.RetryStrategyFactory;
import com.xjtu.yangrpc.fault.tolerant.TolerantStrategy;
import com.xjtu.yangrpc.fault.tolerant.TolerantStrategyFactory;
import com.xjtu.yangrpc.loadbalancer.LoadBalancer;
import com.xjtu.yangrpc.loadbalancer.LoadBalancerFactory;
import com.xjtu.yangrpc.model.RpcRequest;
import com.xjtu.yangrpc.model.RpcResponse;
import com.xjtu.yangrpc.model.ServiceMetaInfo;
import com.xjtu.yangrpc.registry.Registry;
import com.xjtu.yangrpc.registry.RegistryFactory;
import com.xjtu.yangrpc.server.tcp.VertxTcpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务代理（JDK 动态代理）
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
//        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        // 从注册中心获取服务提供者请求地址
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            throw new RuntimeException("暂无服务地址");
        }

        // 负载均衡
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        Map<String, Object> requestParams = new HashMap<>(); // 将调用方法名（请求路径）作为负载均衡参数
        requestParams.put("methodName", rpcRequest.getMethodName());
        ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

        // 发送 TCP 请求
        // 使用重试机制
        RpcResponse rpcResponse = null;
        try {
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
//            int i = 1/0;
            rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
        } catch (Exception e) {
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
            rpcResponse = tolerantStrategy.doTolerant(null, e);
        }
        return rpcResponse.getData();
    }
}
