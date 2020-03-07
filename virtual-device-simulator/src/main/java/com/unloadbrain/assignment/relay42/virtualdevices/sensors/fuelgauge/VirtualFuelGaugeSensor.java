package com.unloadbrain.assignment.relay42.virtualdevices.sensors.fuelgauge;

import com.unloadbrain.assignment.relay42.virtualdevices.sensors.Sensor;
import com.unloadbrain.assignment.relay42.virtualdevices.service.Gateway;
import com.unloadbrain.assignment.relay42.virtualdevices.service.RandomDataStreamEmitter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class VirtualFuelGaugeSensor extends Sensor<FuelGaugeData> {

    public VirtualFuelGaugeSensor(Gateway gateway, RandomDataStreamEmitter randomDataStreamEmitter) {
        super(gateway, UUID.randomUUID().toString());
        pairing();
        randomDataStreamEmitter.emitRandomIntsStream(1, 5, 10000,
                reading -> publishChange(new FuelGaugeData(reading)));
    }

    @Override
    public String getType() {
        return "FuelGaugeSensor";
    }

}
