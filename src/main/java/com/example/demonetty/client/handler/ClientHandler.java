package com.example.demonetty.client.handler;

/**
 * @author kam
 *
 * <p>
 *
 * </p>
 */

import com.example.demonetty.domain.response.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * I/O数据读写处理类
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    //处理服务端返回的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcResponse rpcResponse = (RpcResponse) msg;
        System.out.println("接受到server响应数据: " + rpcResponse.toString());
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
