package com.example.client.config;

import com.example.client.ClientChannelInitializer;
import com.example.client.NettyClient;
import com.example.client.handler.ClientHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kam
 *
 * <p>
 *
 * </p>
 */
@Data
@Configuration
public class NettyClientConfig {

    @Value("${netty.server-host}")
    private String serverHost;

    @Value("${netty.server-port}")
    private int serverPort;

    @Autowired
    private ClientHandler clientHandler;

    @Bean
    public NettyClient nettyClient() {
        return new NettyClient(NioSocketChannel.class,
                new ClientChannelInitializer(clientHandler), serverHost, serverPort);
    }

}
