package com.xjtu.example.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xjtu.example.common.model.User;
import com.xjtu.example.common.service.UserService;
import com.xjtu.yangrpc.model.RpcRequest;
import com.xjtu.yangrpc.model.RpcResponse;
import com.xjtu.yangrpc.serializer.JdkSerializer;
import com.xjtu.yangrpc.serializer.Serializer;

import java.io.IOException;

/**
 * 静态代理 (为每一个特定类型的接口或对象，编写一个代理类)
 * 不是复制粘贴服务提供者 UserServiceImpl 中的代码，而是要构造 HTTP 请求去调用服务提供者。
 */
public class UserServiceProxy implements UserService {
    @Override
    public User getUser(User user) {
        // 指定序列化器
        Serializer serializer = new JdkSerializer();
        // 发请求
        RpcRequest rpcRequest = RpcRequest.builder().serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();
        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            try(HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
