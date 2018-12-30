package com.eric.hydra.scan;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.os.Build;
import android.os.ParcelUuid;
import android.util.SparseArray;

import java.util.List;
import java.util.Map;

/**
 * Created by wanglikun on 2018/12/29
 */
public class Response {

    // Remote Bluetooth device.
    private BluetoothDevice mDevice;

    // Received signal strength.
    private int mRssi;

    // Flags of the advertising data.
    private final int mAdvertiseFlags;

    private final List<ParcelUuid> mServiceUuids;

    private final SparseArray<byte[]> mManufacturerSpecificData;

    private final Map<ParcelUuid, byte[]> mServiceData;

    // Transmission power level(in dB).
    private final int mTxPowerLevel;

    // Local name of the Bluetooth LE device.
    private final String mDeviceName;

    // Raw bytes of scan record.
    private final byte[] mBytes;


    public Response(BluetoothDevice device, int rssi, com.eric.hydra.system.ScanRecord record) {
        if (record != null) {
            mBytes = record.getBytes();
            mAdvertiseFlags = record.getAdvertiseFlags();
            mServiceData = record.getServiceData();
            mServiceUuids = record.getServiceUuids();
            mTxPowerLevel = record.getTxPowerLevel();
            mDeviceName = record.getDeviceName();
            mManufacturerSpecificData = record.getManufacturerSpecificData();
        } else {
            mBytes = null;
            mAdvertiseFlags = 0;
            mServiceData = null;
            mServiceUuids = null;
            mTxPowerLevel = 0;
            mDeviceName = null;
            mManufacturerSpecificData = null;
        }
        this.mDevice = device;
        this.mRssi = rssi;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Response(BluetoothDevice device, int rssi, ScanRecord record) {
        if (record != null) {
            mBytes = record.getBytes();
            mAdvertiseFlags = record.getAdvertiseFlags();
            mServiceData = record.getServiceData();
            mServiceUuids = record.getServiceUuids();
            mTxPowerLevel = record.getTxPowerLevel();
            mDeviceName = record.getDeviceName();
            mManufacturerSpecificData = record.getManufacturerSpecificData();
        } else {
            mBytes = null;
            mAdvertiseFlags = 0;
            mServiceData = null;
            mServiceUuids = null;
            mTxPowerLevel = 0;
            mDeviceName = null;
            mManufacturerSpecificData = null;
        }
        this.mDevice = device;
        this.mRssi = rssi;
    }

    public BluetoothDevice getDevice() {
        return mDevice;
    }

    public int getRssi() {
        return mRssi;
    }

    public int getAdvertiseFlags() {
        return mAdvertiseFlags;
    }

    public List<ParcelUuid> getServiceUuids() {
        return mServiceUuids;
    }

    public SparseArray<byte[]> getManufacturerSpecificData() {
        return mManufacturerSpecificData;
    }

    public Map<ParcelUuid, byte[]> getServiceData() {
        return mServiceData;
    }

    public int getTxPowerLevel() {
        return mTxPowerLevel;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public byte[] getBytes() {
        return mBytes;
    }
}
