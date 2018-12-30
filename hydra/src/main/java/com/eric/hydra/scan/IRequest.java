package com.eric.hydra.scan;

/**
 * Created by wanglikun on 2018/12/29
 */
public interface IRequest {
    boolean isTarget(Response response);

    long getTimeout();
}