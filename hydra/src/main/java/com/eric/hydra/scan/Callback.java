package com.eric.hydra.scan;

import com.eric.hydra.constant.Error;

/**
 * Created by wanglikun on 2018/12/30
 */
public interface Callback {
    void onInterrupt(Error error);

    void onResponse(Response response);
}
