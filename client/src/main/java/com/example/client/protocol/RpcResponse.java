package com.example.client.protocol;

import lombok.Data;

/**
 * @author kam
 *
 * <p>
 * 响应包装类
 * </p>
 */
@Data
public class RpcResponse {

    private String id;
    private Object data;
    private int code;
    private String msg;

    @Override
    public String toString() {
        return "RpcResponse{" + "id='" + id + '\'' + ", data=" + data + ", code=" + code + ", msg=" + msg + '}';
    }
}