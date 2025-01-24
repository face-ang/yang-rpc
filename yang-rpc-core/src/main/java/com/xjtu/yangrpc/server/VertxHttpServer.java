package com.xjtu.yangrpc.server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer {
    @Override
    public void doStart(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();
        // 创建 HTTP 服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();
        /**
         * 监听端口并处理请求
         * 处理 HTTP 请求的整体信息（例如请求方法、URI等）。每当服务器收到一个 HTTP 请求时，它会触发这个处理器。
         * lambda 表达式中的匿名内部类是 Handler<HttpServerRequest> 的实现类
         */
        server.requestHandler(new HttpServerHandler());
        // 启动 HTTP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server is now listening on port " + port);
            } else {
                System.err.println("Failed to start server: " + result.cause());
            }
        });
    }
}
