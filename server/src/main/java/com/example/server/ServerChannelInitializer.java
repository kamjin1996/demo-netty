package com.example.server;

import com.example.server.protocol.RpcRequest;
import com.example.server.protocol.RpcResponse;
import com.example.server.protocol.encode.RpcDecoder;
import com.example.server.protocol.encode.RpcEncoder;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kam
 *
 * <p>
 * 服务端channel初始化类
 * </p>
 */
@Slf4j
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private ChannelInboundHandlerAdapter serverHandler;

    public ServerChannelInitializer() {
    }

    public ServerChannelInitializer(ChannelInboundHandlerAdapter serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        log.info("init channel...");
        ch.pipeline()
                //顺序不能乱，解码请求，编码结果，与client端相对立
                .addLast(newRpcDecoder())
                .addLast(newRpcEncoder())
                .addLast(serverHandler);
    }

    /**
     * 编码结果
     *
     * @return
     */
    private RpcEncoder newRpcEncoder() {
        return new RpcEncoder(RpcResponse.class);
    }

    /**
     * 解码请求
     *
     * @return
     */
    private RpcDecoder newRpcDecoder() {
        return new RpcDecoder(RpcRequest.class);
    }
}
