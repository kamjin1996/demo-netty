package com.example.client;

import com.example.client.protocol.RpcRequest;
import com.example.client.protocol.RpcResponse;
import com.example.client.protocol.encode.RpcDecoder;
import com.example.client.protocol.encode.RpcEncoder;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kam
 *
 * <p>
 * 客户端channel初始化类
 * </p>
 */
@Slf4j
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private ChannelInboundHandlerAdapter clientHandler;

    public ClientChannelInitializer() {
    }

    public ClientChannelInitializer(ChannelInboundHandlerAdapter clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        log.info("init channel...");
        ch.pipeline()
                //新增换行解码器，防止粘包拆包问题
                .addLast(new LineBasedFrameDecoder(1024))
                //顺序不能乱，编码请求，解码结果，与server端相对立
                .addLast(newRpcEncoder())
                .addLast(newRpcDecoder())
                .addLast(clientHandler);
    }

    /**
     * 编码请求
     *
     * @return
     */
    private RpcEncoder newRpcEncoder() {
        return new RpcEncoder(RpcRequest.class);
    }

    /**
     * 解码结果
     *
     * @return
     */
    private RpcDecoder newRpcDecoder() {
        return new RpcDecoder(RpcResponse.class);
    }
}
