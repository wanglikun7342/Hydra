package com.eric.hydra.constant;

/**
 * Created by wanglikun on 2018/12/29
 */
public class Error {
    /**
     * Fails to start scan as BLE scan with the same settings is already started by the app.
     */
    private static final int SCAN_FAILED_ALREADY_STARTED = 1;

    /**
     * Fails to start scan as app cannot be registered.
     */
    private static final int SCAN_FAILED_APPLICATION_REGISTRATION_FAILED = 2;

    /**
     * Fails to start scan due an internal error
     */
    public static final int SCAN_FAILED_INTERNAL_ERROR = 3;

    /**
     * Fails to start power optimized scan as this feature is not supported.
     */
    public static final int SCAN_FAILED_FEATURE_UNSUPPORTED = 4;

    /**
     * Fails to start scan as it is out of hardware resources.
     *
     *
     */
    public static final int SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES = 5;

    /**
     * Fails to start scan as application tries to scan too frequently.
     *
     */
    public static final int SCAN_FAILED_SCANNING_TOO_FREQUENTLY = 6;

    public final int code;

    public final String msg;

    public Error(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Error(int code) {
        this.code = code;
        switch (code) {
            case SCAN_FAILED_ALREADY_STARTED: {
                msg = "scan failed already started";
            }
            break;
            case SCAN_FAILED_APPLICATION_REGISTRATION_FAILED: {
                msg = "scan failed application registration failed";
            }
            break;
            case SCAN_FAILED_INTERNAL_ERROR: {
                msg = "scan failed internal error";
            }
            break;
            case SCAN_FAILED_FEATURE_UNSUPPORTED: {
                msg = "scan failed feature unsupported";
            }
            break;
            case SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES: {
                msg = "scan failed out of hardware resources";
            }
            break;
            case SCAN_FAILED_SCANNING_TOO_FREQUENTLY: {
                msg = "scan failed scanning too frequently";
            }
            break;
            default:
                msg = "";
                break;
        }
    }

    @Override
    public String toString() {
        return "code: " + code + ", msg: " + msg;
    }

    public static Error BLE_DISABLE = new Error(1001, "ble disable");

    public static Error BLE_NULL_ADAPTER_OR_SCANNER = new Error(1002, "ble adapter or scanner is null");

    public static Error BLE_NOT_SUPPORT = new Error(1003, "ble not support");

    public static Error BLE_START_FAILED = new Error(1004, "ble start failed");

    public static Error BLE_SCAN_TIMEOUT = new Error(1005, "ble scan timeout");
}
