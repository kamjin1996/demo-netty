package com.example.server.config;

import com.example.server.NettyServer;
import com.example.server.ServerChannelInitializer;
import com.example.server.handler.ServerHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kam
 *
 * <p>
 *
 * </p>
 */
@Configuration
@Data
public class NettyServerConfig {

    @Autowired
    private ServerHandler serverHandler;

    @Bean
    public NettyServer nettyServer() {
        return new NettyServer(NioServerSocketChannel.class, new ServerChannelInitializer(serverHandler),
                new NioEventLoopGroup(), new NioEventLoopGroup());
    }
}
