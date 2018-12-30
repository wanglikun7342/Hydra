package com.eric.hydra.connect;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglikun on 2018/12/30
 */
public class Session {
    private final BluetoothDevice mDevice;

    private BluetoothGatt mGatt;

    private List<SessionCallback> mCallbacks = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (mCallbacks != null) {
                for (SessionCallback callback : mCallbacks) {
                    callback.onConnectionStateChange(Session.this, status, newState);
                }
            }
        }

        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            if (mCallbacks != null) {
                for (SessionCallback callback : mCallbacks) {
                    callback.onPhyUpdate(Session.this, txPhy, rxPhy, status);
                }
            }
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            if (mCallbacks != null) {
                for (SessionCallback callback : mCallbacks) {
                    callback.onPhyRead(Session.this, txPhy, rxPhy, status);
                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (mCallbacks != null) {
                for (SessionCallback callback : mCallbacks) {
                    callback.onServicesDiscovered(Session.this, status);
                }
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (mCallbacks != null) {
                for (SessionCallback callback : mCallbacks) {
                    callback.onCharacteristicRead(Session.this, characteristic, status);
                }
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (mCallbacks != null) {
                for (SessionCallback callback : mCallbacks) {
                    callback.onCharacteristicWrite(Session.this, characteristic, status);
                }
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (mCallbacks != null) {
                for (SessionCallback callback : mCallbacks) {
                    callback.onCharacteristicChanged(Session.this, characteristic);
                }
            }
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (mCallbacks != null) {
                for (SessionCallback callback : mCallbacks) {
                    callback.onDescriptorRead(Session.this, descriptor, status);
                }
            }
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (mCallbacks != null) {
                for (SessionCallback callback : mCallbacks) {
                    callback.onDescriptorWrite(Session.this, descriptor, status);
                }
            }
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            if (mCallbacks != null) {
                for (SessionCallback callback : mCallbacks) {
                    callback.onReliableWriteCompleted(Session.this, status);
                }
            }
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            if (mCallbacks != null) {
                for (SessionCallback callback : mCallbacks) {
                    callback.onReadRemoteRssi(Session.this, rssi, status);
                }
            }
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            if (mCallbacks != null) {
                for (SessionCallback callback : mCallbacks) {
                    callback.onMtuChanged(Session.this, mtu, status);
                }
            }
        }
    };

    public Session(BluetoothDevice device) {
        mDevice = device;
    }

    public Session(String address) {
        if (BluetoothAdapter.getDefaultAdapter() == null) {
            mDevice = null;
        } else {
            mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);
        }
    }

    public void connect(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return;
        }
        if (mDevice == null) {
            return;
        }
        if (mGatt != null) {
            mGatt.disconnect();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mGatt = mDevice.connectGatt(context, false, mGattCallback, BluetoothDevice.TRANSPORT_LE);
        } else {
            mGatt = mDevice.connectGatt(context, false, mGattCallback);
        }
        requestHighConnectionPriority(mGatt);
    }

    public void disconnect() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return;
        }
        if (mGatt == null) {
            return;
        }
        mGatt.disconnect();
    }

    public boolean writeDescriptor(BluetoothGattDescriptor descriptor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return false;
        }
        if (mGatt == null) {
            return false;
        }
        return mGatt.writeDescriptor(descriptor);
    }

    public boolean readDescriptor(BluetoothGattDescriptor descriptor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return false;
        }
        if (mGatt == null) {
            return false;
        }
        return mGatt.readDescriptor(descriptor);
    }

    public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return false;
        }
        if (mGatt == null) {
            return false;
        }
        return mGatt.writeCharacteristic(characteristic);
    }

    public boolean readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return false;
        }
        if (mGatt == null) {
            return false;
        }
        return mGatt.readCharacteristic(characteristic);
    }

    public boolean readRemoteRssi() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return false;
        }
        if (mGatt == null) {
            return false;
        }
        return mGatt.readRemoteRssi();
    }

    public boolean setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return false;
        }
        if (mGatt == null) {
            return false;
        }
        return mGatt.setCharacteristicNotification(characteristic, enable);
    }

    public boolean discoverServices() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return false;
        }
        if (mGatt == null) {
            return false;
        }
        return mGatt.discoverServices();
    }

    private void requestHighConnectionPriority(BluetoothGatt gatt) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH);
        }
    }

    public BluetoothDevice getDevice() {
        return mDevice;
    }

    public void registerCallback(SessionCallback callback) {
        this.mCallbacks.add(callback);
    }

    public void unRegisterCallback(SessionCallback callback) {
        this.mCallbacks.remove(callback);
    }
}
