package com.unloadbrain.assignment.relay42.virtualdevices.sensors.fuelgauge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FuelGaugeData {

    private int reading;

}
