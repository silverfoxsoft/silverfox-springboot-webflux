package com.silverfox.publicsvr.common;

import lombok.Data;
import lombok.ToString;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class CommonResult<T> implements Serializable {
    String msg;
    Integer code;
    T data;

    public static <T> Mono<CommonResult<T>> ok (Mono<T> monoBody) {
        return responseBodyCreate(monoBody,0,null);
    }
    public static <T> Mono<CommonResult<T>> ok (Mono<T> monoBody, String msg) {
        return responseBodyCreate(monoBody,0,msg);
    }

    public static <T> Mono<CommonResult<T>> ok (Mono<T> monoBody, int code, String msg) {
        return responseBodyCreate(monoBody,code,msg);
    }

    public static <T> Mono<CommonResult<T>> failed (Mono<T> monoBody) {
        return responseBodyCreate(monoBody,0,null);
    }

    public static <T> Mono<CommonResult<T>> failed (Mono<T> monoBody, String msg) {
        return responseBodyCreate(monoBody,0,msg);
    }

    public static <T> Mono<CommonResult<T>> failed (Mono<T> monoBody, int code, String msg) {
        return responseBodyCreate(monoBody,code,msg);
    }

    private static <T> Mono<CommonResult<T>> responseBodyCreate(Mono<T> monoData, int code, String msg) {
        return monoData.map(data-> {
            final CommonResult<T> commonResult = new CommonResult<>();
            commonResult.setCode(code);
            commonResult.setData(data);
            commonResult.setMsg(msg);
            return commonResult;
        });
    }

    public static <T>Mono<CommonResult<List<T>>> ok(Flux<T> fluxBody) {
        return fluxBody.collectList().map(data -> {
            final CommonResult<List<T>> commonResult = new CommonResult<>();
            commonResult.setCode(0);
            commonResult.setData(data);
            commonResult.setMsg("success");
            return commonResult;
        });
    }
}
