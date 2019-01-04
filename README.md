# Hydra ![Hydra](https://img.shields.io/github/license/wanglikun7342/Hydra.svg)![hydra](https://img.shields.io/badge/jcenter-1.0.0-blue.svg)![pr welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)
> A lightweight Bluetooth scan/connect client for Android applications.

### 1. Dependency

```groovy
dependencies {
	...
	implementation 'com.eric.hydra:hydra:1.0.0'
	...
}
```

Please use [the lastest release](releaseNotes.md)。

### 2. Guide

- ##### Request a scan

```java
Request request = Request.Builder()
        .timeout(1000)
        .build();
HydraClient client = new HydraClient();
client.scan(request, new Callback() {
    @Override
    public void onInterrupt(Error error) {
    }

    @Override
    public void onResponse(Response response) {
    }
});
```

- ##### Remove a scan

```java
client.removeScan(request)
```

- ##### Create a connect session

```java
Session session = new Session(response.getDevice());
session.connect(context);
```

- ##### Communication with connect session

```java
session.registerCallback(new SessionCallback() {
    @Override
    public void onCharacteristicWrite(Session session, BluetoothGattCharacteristic characteristic, int status) {

    }
});
session.writeCharacteristic(characteristic);
```