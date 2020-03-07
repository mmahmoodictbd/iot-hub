package com.unloadbrain.assignment.relay42.virtualdevices.sensors.heartrate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HeartRateData {

    private int rate;

}
