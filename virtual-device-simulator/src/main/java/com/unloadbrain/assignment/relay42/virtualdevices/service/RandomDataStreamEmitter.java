package com.unloadbrain.assignment.relay42.virtualdevices.service;

import java.util.Random;
import java.util.SplittableRandom;
import java.util.function.Consumer;

public class RandomDataStreamEmitter {

    private static final Random random = new Random();

    public void emitRandomIntsStream(int randomNumberOrigin, int randomNumberBound, int randomSleepBound,
                                     final Consumer<Integer> consumer) {

        Thread thread = new Thread("RandomIntsDataStreamThread") {
            public void run() {
                new SplittableRandom()
                        .ints(randomNumberOrigin, randomNumberBound)
                        .forEach(reading -> {
                            randomSleep(randomSleepBound);
                            consumer.accept(reading);
                        });
            }
        };

        thread.start();
    }

    public void emitRandomDoubleStream(double randomNumberOrigin, double randomNumberBound, int randomSleepBound,
                                       final Consumer<Double> consumer) {

        Thread thread = new Thread("RandomDoublesDataStreamThread") {
            public void run() {
                new SplittableRandom()
                        .doubles(randomNumberOrigin, randomNumberBound)
                        .forEach(reading -> {
                            randomSleep(randomSleepBound);
                            consumer.accept(reading);
                        });
            }
        };

        thread.start();
    }

    private void randomSleep(int randomSleepBound) {
        try {
            Thread.sleep(random.nextInt(randomSleepBound));
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread sleep interrupted!");
        }
    }
}
