package com.eric.hydra.scan;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Build;
import android.os.Message;

import com.eric.hydra.constant.Error;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by wanglikun on 2018/12/29
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Scanner extends BaseScanner {
    private BluetoothAdapter mAdapter;

    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            if (isStopped.get()) {
                return;
            }
            if (result == null || result.getScanRecord() == null) {
                return;
            }
            Response response = new Response(result.getDevice(), result.getRssi(), result.getScanRecord());
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

        @Override
        public void onScanFailed(int errorCode) {
            if (isStopped.get()) {
                return;
            }
            for (IRequest request : mCaches.keySet()) {
                onInterrupt(request, new Error(errorCode));
            }
            stopInternal();
        }
    };

    public Scanner() {
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

        if (mAdapter == null || mAdapter.getBluetoothLeScanner() == null) {
            onInterrupt(request, Error.BLE_NULL_ADAPTER_OR_SCANNER);
            return;
        }
        if (!mAdapter.isEnabled()) {
            onInterrupt(request, Error.BLE_DISABLE);
            return;
        }
        try {
            BluetoothLeScanner scanner = mAdapter.getBluetoothLeScanner();
            scanner.stopScan(mScanCallback);
            scanner.startScan(null, getSettings(), mScanCallback);
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
        if (mAdapter == null || mAdapter.getBluetoothLeScanner() == null) {
            return;
        }
        if (mAdapter.isEnabled()) {
            return;
        }
        try {
            BluetoothLeScanner scanner = mAdapter.getBluetoothLeScanner();
            scanner.stopScan(mScanCallback);
            isStopped.set(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ScanSettings getSettings() {
        ScanSettings.Builder settingBuilder = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
        return settingBuilder.build();
    }
}

