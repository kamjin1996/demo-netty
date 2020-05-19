package com.example.server;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Async
    @Override
    public void run(String... args) throws InterruptedException {
        //初始化服务端可连接队列,指定了队列的大小128
        //保持长连接
        this.nettyServer.createBinder(ChannelOption.SO_BACKLOG, 128, ChannelOption.SO_KEEPALIVE, true)
                .bind(8891);
    }
}
