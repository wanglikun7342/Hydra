package com.eric.hydra.scan;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Message;

import com.eric.hydra.constant.Error;
import com.eric.hydra.system.ScanRecord;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by wanglikun on 2018/12/29
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class LowScanner extends BaseScanner {
    private BluetoothAdapter mAdapter;

    private BluetoothAdapter.LeScanCallback mScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] record) {
            if (isStopped.get()) {
                return;
            }
            ScanRecord scanRecord = ScanRecord.parseFromBytes(record);
            if (scanRecord == null) {
                return;
            }
            Response response = new Response(device, rssi, scanRecord);
            for (IRequest request : mCaches.keySet()) {
                if (!request.isTarget(response)) {
                    continue;
                }
                onResponse(request, response);
            }
            if (mCaches.keySet().isEmpty()) {
                stopInternal();
            }
        }
    };

    public LowScanner() {
        isStopped = new AtomicBoolean(true);
        mCaches = new HashMap<>();
        mAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public void scan(IRequest request, Callback callback) {
        if (request == null) {
            return;
        }

        mCaches.put(request, callback);

        if (request.getTimeout() > 0) {
            Message msg = Message.obtain();
            msg.what = HANDLE_TIMEOUT;
            msg.obj = request;
            mHandler.sendMessageDelayed(msg, request.getTimeout());
        }

        if (mCaches.keySet().size() > 1) {
            return;
        }

        if (mAdapter == null) {
            onInterrupt(request, Error.BLE_NULL_ADAPTER_OR_SCANNER);
            return;
        }
        if (!mAdapter.isEnabled()) {
            onInterrupt(request, Error.BLE_DISABLE);
            return;
        }
        try {
            mAdapter.stopLeScan(mScanCallback);
            mAdapter.startLeScan(mScanCallback);
            isStopped.set(false);
        } catch (Exception e) {
            e.printStackTrace();
            onInterrupt(request, Error.BLE_START_FAILED);
            stopInternal();
        }
    }

    @Override
    public void removeScan(IRequest request) {
        if (request == null) {
            return;
        }
        mCaches.remove(request);
        if (mCaches.keySet().isEmpty()) {
            stopInternal();
        }
    }

    @Override
    public void removeAllScan() {
        mCaches.clear();
        stopInternal();
    }

    @Override
    public boolean isScanning() {
        return !mCaches.isEmpty();
    }

    private void stopInternal() {
        if (mAdapter == null) {
            return;
        }
        if (mAdapter.isEnabled()) {
            return;
        }
        try {
            mAdapter.stopLeScan(mScanCallback);
            isStopped.set(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
