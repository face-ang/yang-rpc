package com.xjtu.yangrpc.springboot.starter.annotation;

import com.xjtu.yangrpc.springboot.starter.bootstrap.RpcConsumerBootstrap;
import com.xjtu.yangrpc.springboot.starter.bootstrap.RpcInitBootstrap;
import com.xjtu.yangrpc.springboot.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用 Rpc 注解: 用于全局标识项目需要引入 RPC 框架、执行初始化方法。
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {

    /**
     * 需要启动 server
     *
     * @return
     */
    boolean needServer() default true;
}
