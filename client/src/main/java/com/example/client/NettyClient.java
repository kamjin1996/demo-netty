package com.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author kam
 *
 * <p>
 * netty客户端
 * </p>
 */
@Data
@Slf4j
public class NettyClient {

    private String host;

    private int port;

    private Channel channel;

    private ChannelInitializer<?> channelInitializer;

    private Class<? extends SocketChannel> socketChannelCls;

    public NettyClient() {
    }

    public NettyClient(Class<? extends SocketChannel> socketChannelCls, ChannelInitializer<?> channelInitializer, String host, int port) {
        this.socketChannelCls = socketChannelCls;
        this.channelInitializer = channelInitializer;
        this.host = host;
        this.port = port;
    }

    public Channel getChannel() {
        if (Objects.isNull(channel)) {
            throw new IllegalStateException("netty server never start");
        }
        return channel;
    }

    public <T> NettyClientStarter<T> createStarter(ChannelOption<T> option, T optionArg) {
        return new NettyClientStarter<>(this, option, optionArg);
    }

    @Data
    public static class NettyClientStarter<T> {

        private NettyClient nettyClient;

        private ChannelOption<T> option;
        private T optionArg;

        public NettyClientStarter(NettyClient nettyClient, ChannelOption<T> option, T optionArg) {
            this.nettyClient = nettyClient;
            this.option = option;
            this.optionArg = optionArg;
        }

        public void start() throws InterruptedException {
            final EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .option(option, optionArg)
                    .channel(nettyClient.getSocketChannelCls())
                    .handler(nettyClient.getChannelInitializer());

            //发起异步连接请求，绑定连接端口和host信息
            final ChannelFuture future = bootstrap.connect(nettyClient.getHost(), nettyClient.getPort())
                    .sync();
            future.addListener((ChannelFutureListener) arg0 -> {
                if (!future.isSuccess()) {
                    log.error("netty client start failed", future.cause());
                    //关闭线程组
                    group.shutdownGracefully();
                }
                log.info("netty client start success");
            });

            this.nettyClient.setChannel(future.channel());
        }
    }
}

