package com.unloadbrain.assignment.relay42.virtualdevices.service;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RandomDataStreamEmitterTest {

    @Test
    public void shouldReturnIntsStreamOnRandomSleep() throws InterruptedException {

        // Given
        RandomDataStreamEmitter emitter = new RandomDataStreamEmitter();

        List<Integer> numbers = new ArrayList<>();

        // When
        emitter.emitRandomIntsStream(1, 5, 1000, integer -> numbers.add(integer));
        Thread.sleep(3000);

        // Then
        assertTrue(numbers.size() > 0);
        numbers.forEach(num -> assertTrue(num >= 1 && num <= 5));
    }

    @Test
    public void shouldReturnDoublesStreamOnRandomSleep() throws InterruptedException {

        // Given
        RandomDataStreamEmitter emitter = new RandomDataStreamEmitter();

        List<Double> numbers = new ArrayList<>();

        // When
        emitter.emitRandomDoubleStream(0, 1, 1000, num -> numbers.add(num));
        Thread.sleep(3000);

        // Then
        assertTrue(numbers.size() > 0);
        numbers.forEach(num -> assertTrue(num >= 0 && num <= 1));
    }

}