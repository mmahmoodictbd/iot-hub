package com.unloadbrain.kafka.connect.influxdb.prop;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KafkaConnectInfluxDBSinkProperties {

    private final String kafkaBootstrapServers;

    private final String kafkaConsumerGroupId;

    private final String kafkaConsumerAutoOffsetReset;

    private final String kafkaTopics;

    private final String influxDbUrl;

    private final String influxDbUsername;

    private final String influxDbPassword;

    private final String influxDbDatabase;

    private final String influxDbRetentionPolicy;

    private final int influxDbReadTimeoutInMs;

}
