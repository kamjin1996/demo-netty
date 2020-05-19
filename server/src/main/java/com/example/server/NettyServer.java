package com.example.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kam
 *
 * <p>
 * netty服务
 * </p>
 */
@Data
@Slf4j
public class NettyServer {

    private ChannelInitializer<?> serverChannelInitializer;

    private Class<? extends ServerChannel> serverChannelCls;

    /**
     * 负责处理TCP/IP连接的
     */
    private EventLoopGroup parentGroup;

    /**
     * 负责处理Channel(通道)的I/O事件
     */
    private EventLoopGroup childGroup;

    public NettyServer() {
    }

    public NettyServer(Class<? extends ServerChannel> serverChannelCls, ChannelInitializer<?> serverChannelInitializer, EventLoopGroup parentGroup, EventLoopGroup childGroup) {
        this.serverChannelCls = serverChannelCls;
        this.serverChannelInitializer = serverChannelInitializer;
        this.parentGroup = parentGroup;
        this.childGroup = childGroup;
    }

    public <P, C> NettyServerBinder<P, C> createBinder(ChannelOption<P> option, P optionArg, ChannelOption<C> childOption, C childOptionArg) {
        return new NettyServerBinder<>(this, option, optionArg, childOption, childOptionArg);
    }

    @Data
    public static class NettyServerBinder<P, C> {

        private NettyServer nettyServer;

        private ChannelOption<P> option;
        private P optionArg;
        private ChannelOption<C> childOption;
        private C childOptionArg;

        private NettyServerBinder(NettyServer nettyServer, ChannelOption<P> option, P optionArg, ChannelOption<C> childOption, C childOptionArg) {
            this.nettyServer = nettyServer;
            this.option = option;
            this.optionArg = optionArg;
            this.childOption = childOption;
            this.childOptionArg = childOptionArg;
        }

        public void bind(int port) throws InterruptedException {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(nettyServer.getParentGroup(), nettyServer.getChildGroup())
                    .channel(nettyServer.getServerChannelCls())
                    .option(option, optionArg)
                    .childOption(childOption, childOptionArg)
                    .childHandler(nettyServer.getServerChannelInitializer());

            //绑定监听端口，调用sync同步阻塞方法等待绑定操作完
            ChannelFuture future = serverBootstrap.bind(port).sync();

            if (!future.isSuccess()) {
                log.error("netty server start failed", future.cause());
                //关闭线程组
                nettyServer.getParentGroup().shutdownGracefully();
                nettyServer.getParentGroup().shutdownGracefully();
            }

            log.info("netty server start success");
            //成功绑定到端口之后,给channel增加一个 管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程。
            future.channel().closeFuture().sync();
        }
    }
}