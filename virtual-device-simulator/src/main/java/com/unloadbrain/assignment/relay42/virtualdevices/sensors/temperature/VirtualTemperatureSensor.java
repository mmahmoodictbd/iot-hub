package com.unloadbrain.assignment.relay42.virtualdevices.sensors.temperature;

import com.unloadbrain.assignment.relay42.virtualdevices.sensors.Sensor;
import com.unloadbrain.assignment.relay42.virtualdevices.service.Gateway;
import com.unloadbrain.assignment.relay42.virtualdevices.service.RandomDataStreamEmitter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class VirtualTemperatureSensor extends Sensor<TemperatureData> {

    public VirtualTemperatureSensor(Gateway gateway, RandomDataStreamEmitter randomDataStreamEmitter) {
        super(gateway, UUID.randomUUID().toString());
        pairing();
        randomDataStreamEmitter.emitRandomDoubleStream(40, 45, 10000,
                reading -> publishChange(new TemperatureData(reading)));
    }

    @Override
    public String getType() {
        return "TemperatureSensor";
    }

}
