package com.eric.hydra.scan;

import android.bluetooth.BluetoothDevice;
import android.os.ParcelUuid;
import android.text.TextUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by wanglikun on 2018/12/29
 */
public class Request implements IRequest {

    private final String address;

    private final UUID[] filterUUIDs;

    private final long timeout;

    private Request(String address, UUID[] filterUUIDs, long timeout) {
        this.address = address;
        this.filterUUIDs = filterUUIDs;
        this.timeout = timeout;
    }

    @Override
    public boolean isTarget(Response response) {
        if (response == null) {
            return false;
        }
        BluetoothDevice device = response.getDevice();
        if (device == null) {
            return false;
        }
        if (address != null && TextUtils.equals(device.getAddress(), address)) {
            return true;
        }
        if (filterUUIDs != null) {
            List<ParcelUuid> parcelUuids = response.getServiceUuids();
            if (parcelUuids != null) {
                for (ParcelUuid parcelUUID : parcelUuids) {
                    for (UUID filterUUID : filterUUIDs) {
                        if (filterUUID.equals(parcelUUID.getUuid())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public long getTimeout() {
        return timeout;
    }

    public static class Builder {

        private String address = null;

        private UUID[] filterUUIDs = null;

        private long timeout = -1;

        public Request build() {
            return new Request(address, filterUUIDs, timeout);
        }

        public Builder timeout(long timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder filter(UUID[] filterUUIDs) {
            this.filterUUIDs = filterUUIDs;
            return this;
        }

        public Builder filter(String address) {
            this.address = address;
            return this;
        }
    }

    public static Builder Builder() {
        return new Builder();
    }
}
