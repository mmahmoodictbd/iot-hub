package com.unloadbrain.assignment.relay42.virtualdevices.sensors.heartrate;

import com.unloadbrain.assignment.relay42.virtualdevices.sensors.Sensor;
import com.unloadbrain.assignment.relay42.virtualdevices.service.Gateway;
import com.unloadbrain.assignment.relay42.virtualdevices.service.RandomDataStreamEmitter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class VirtualHeartRateMonitor extends Sensor<HeartRateData> {

    public VirtualHeartRateMonitor(Gateway gateway, RandomDataStreamEmitter randomDataStreamEmitter) {
        super(gateway, UUID.randomUUID().toString());
        pairing();
        randomDataStreamEmitter.emitRandomIntsStream(65, 100, 10000,
                reading -> publishChange(new HeartRateData(reading)));
    }

    @Override
    public String getType() {
        return "HeartRateMonitor";
    }

}
