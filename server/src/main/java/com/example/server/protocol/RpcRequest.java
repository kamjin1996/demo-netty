package com.example.server.protocol;

import lombok.Data;

/**
 * @author kam
 *
 * <p>
 * 请求包装类
 * </p>
 */
@Data
public class RpcRequest {

    private String id;
    private Object data;

    @Override
    public String toString() {
        return "RpcRequest{" + "id='" + id + '\'' + ", data=" + data + '}';
    }
}
