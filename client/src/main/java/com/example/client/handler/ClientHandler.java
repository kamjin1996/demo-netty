package com.example.client.handler;

import com.example.client.protocol.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

/**
 * <p>
 * I/O数据读写处理类
 * </p>
 *
 * @author kam
 */
@Component
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    /**
     * 处理服务端返回的数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        System.out.println("接受到server响应数据: " + msg.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
