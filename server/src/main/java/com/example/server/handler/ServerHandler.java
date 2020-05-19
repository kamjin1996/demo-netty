package com.example.server.handler;

import com.example.server.protocol.RpcRequest;
import com.example.server.protocol.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author kam
 *
 * <p>
 * I/O数据读写处理类
 * </p>
 */
@Component
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    //接受client发送的消息
    @Override
    public void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        System.out.println("接收到客户端信息:" + msg.toString());
        //返回的数据结构
        RpcResponse response = new RpcResponse();
        response.setId(UUID.randomUUID().toString());
        response.setData("server响应结果");
        response.setCode(200);
        ctx.writeAndFlush(response + "\n");
    }

    //通知处理器最后的channelRead()是当前批处理中的最后一条消息时调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端接收数据完毕..");
        ctx.flush();
    }

    //读操作时捕获到异常时调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    //客户端去和服务端连接成功时触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("hello client" + "\n");
    }

}
