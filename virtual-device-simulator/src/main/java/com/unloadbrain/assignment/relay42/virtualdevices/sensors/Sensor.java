package com.unloadbrain.assignment.relay42.virtualdevices.sensors;

import com.unloadbrain.assignment.relay42.virtualdevices.service.Gateway;

import java.util.Observable;

public abstract class Sensor<T> extends Observable {

    private final Gateway gateway;
    private String deviceId;

    public Sensor(Gateway gateway, String deviceId) {
        this.gateway = gateway;
        this.deviceId = deviceId;
    }

    public String getDeviceUuid() {
        return deviceId;
    }

    public void pairing() {
        gateway.register(this);
    }

    public void unpairing() {
        gateway.deregister(this);
    }

    public abstract String getType();

    public void publishChange(T data) {
        setChanged();
        notifyObservers(data);
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", getType(), getDeviceUuid());
    }
}
