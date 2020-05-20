package com.example.client;

import com.example.client.protocol.RpcRequest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

@Slf4j
@EnableAsync
@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    @Autowired
    private NettyClient nettyClient;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Async
    @Override
    public void run(String... args) throws Exception {
        this.nettyClient.createStarter(ChannelOption.TCP_NODELAY, true)
                .start();
        Channel channel = nettyClient.getChannel();
        //消息体
        RpcRequest request = new RpcRequest();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String next = scanner.next();

            if (Objects.equals(next, "EXIT")) {
                log.info("stop send...");
                break;
            }

            request.setId(UUID.randomUUID().toString());
            request.setData(next);
            //channel对象可重用，保存在内存中，可供其它地方发送消息
            channel.writeAndFlush(request);
        }
    }
}
