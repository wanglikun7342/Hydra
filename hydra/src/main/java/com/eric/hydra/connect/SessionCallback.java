package com.eric.hydra.connect;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;

/**
 * Created by wanglikun on 2018/12/30
 */
public abstract class SessionCallback {

    public void onPhyUpdate(Session session, int txPhy, int rxPhy, int status) {
    }

    public void onPhyRead(Session session, int txPhy, int rxPhy, int status) {
    }

    public void onConnectionStateChange(Session session, int status,
                                        int newState) {
    }

    public void onServicesDiscovered(Session session, int status) {
    }

    public void onCharacteristicRead(Session session, BluetoothGattCharacteristic characteristic,
                                     int status) {
    }

    public void onCharacteristicWrite(Session session,
                                      BluetoothGattCharacteristic characteristic, int status) {
    }

    public void onCharacteristicChanged(Session session,
                                        BluetoothGattCharacteristic characteristic) {
    }

    public void onDescriptorRead(Session session, BluetoothGattDescriptor descriptor,
                                 int status) {
    }

    public void onDescriptorWrite(Session session, BluetoothGattDescriptor descriptor,
                                  int status) {
    }


    public void onReliableWriteCompleted(Session session, int status) {

    }

    public void onReadRemoteRssi(Session session, int rssi, int status) {
    }

    public void onMtuChanged(Session session, int mtu, int status) {
    }

    public void onConnectionUpdated(Session session, int interval, int latency, int timeout,
                                    int status) {
    }
}
