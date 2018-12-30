package com.eric.hydra.scan;

import com.eric.hydra.constant.Error;

/**
 * Created by wanglikun on 2018/12/29
 */
public class VoidScanner extends BaseScanner {
    public VoidScanner() {
    }

    @Override
    public void scan(IRequest request, final Callback callback) {
        if (isMainThread()) {
            callback.onInterrupt(Error.BLE_NOT_SUPPORT);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onInterrupt(Error.BLE_NOT_SUPPORT);
                }
            });
        }
    }

    @Override
    public void removeScan(IRequest request) {
        // void implement
    }

    @Override
    public void removeAllScan() {
        // void implement
    }

    @Override
    public boolean isScanning() {
        return false;
    }
}
