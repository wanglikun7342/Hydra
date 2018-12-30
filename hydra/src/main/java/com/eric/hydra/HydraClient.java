package com.eric.hydra;

import android.os.Build;

import com.eric.hydra.scan.BaseScanner;
import com.eric.hydra.scan.Callback;
import com.eric.hydra.scan.IRequest;
import com.eric.hydra.scan.LowScanner;
import com.eric.hydra.scan.Scanner;
import com.eric.hydra.scan.VoidScanner;

/**
 * Created by wanglikun on 2018/12/29
 */
public class HydraClient {
    private BaseScanner mScanner;

    public HydraClient() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mScanner = new Scanner();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            mScanner = new LowScanner();
        } else {
            mScanner = new VoidScanner();
        }
    }

    public void scan(IRequest request, Callback callback) {
        mScanner.scan(request, callback);
    }

    public void removeScan(IRequest request) {
        mScanner.removeScan(request);
    }

    public void removeAllScan() {
        mScanner.removeAllScan();
    }
}
