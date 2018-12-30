package com.eric.hydra.scan;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.eric.hydra.constant.Error;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by wanglikun on 2018/12/29
 */
public abstract class BaseScanner {
    static final int HANDLE_TIMEOUT = 1001;

    HashMap<IRequest, Callback> mCaches;

    AtomicBoolean isStopped;

    ScannerHandler mHandler = new ScannerHandler(this);

    public abstract void scan(IRequest request, Callback callback);

    public abstract void removeScan(IRequest request);

    public abstract void removeAllScan();

    abstract boolean isScanning();

    void onResponse(IRequest request, final Response response) {
        if (request == null) {
            return;
        }
        final Callback callback = mCaches.get(request);
        if (callback == null) {
            return;
        }
        if (isMainThread()) {
            callback.onResponse(response);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onResponse(response);
                }
            });
        }
    }

    void onInterrupt(IRequest request, final Error error) {
        if (request == null) {
            return;
        }
        final Callback callback = mCaches.remove(request);
        if (callback == null) {
            return;
        }
        if (isMainThread()) {
            callback.onInterrupt(error);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onInterrupt(error);
                }
            });
        }
    }

    static class ScannerHandler extends Handler {
        WeakReference<BaseScanner> scannerReference;

        ScannerHandler(BaseScanner scanner) {
            super(Looper.getMainLooper());
            scannerReference = new WeakReference<>(scanner);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseScanner scanner = scannerReference.get();
            if (scanner == null) {
                return;
            }
            if (scanner.isStopped.get()) {
                return;
            }
            switch (msg.what) {
                case HANDLE_TIMEOUT:
                    scanner.onInterrupt((IRequest) msg.obj, Error.BLE_SCAN_TIMEOUT);
                    break;
                default:
                    break;
            }
        }
    }

    boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
