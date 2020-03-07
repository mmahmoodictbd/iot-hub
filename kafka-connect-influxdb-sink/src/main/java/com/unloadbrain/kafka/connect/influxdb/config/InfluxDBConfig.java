package com.unloadbrain.kafka.connect.influxdb.config;

import com.unloadbrain.kafka.connect.influxdb.prop.KafkaConnectInfluxDBSinkProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


/**
 * This class provides InfluxDB related configuration and beans.
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class InfluxDBConfig {

    private final KafkaConnectInfluxDBSinkProperties properties;

    @Bean
    public InfluxDB influxDB() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(properties.getInfluxDbReadTimeoutInMs(), TimeUnit.MILLISECONDS);
        InfluxDB influxDB = InfluxDBFactory.connect(properties.getInfluxDbUrl(),
                properties.getInfluxDbUsername(), properties.getInfluxDbPassword(), builder);

        influxDB.query(new Query(String.format("CREATE DATABASE %s",
                properties.getInfluxDbDatabase()), properties.getInfluxDbDatabase()));
        influxDB.setDatabase(properties.getInfluxDbDatabase());

        influxDB.setRetentionPolicy(properties.getInfluxDbRetentionPolicy());
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);

        Pong response = influxDB.ping();
        if ("unknown".equalsIgnoreCase(response.getVersion())) {
            log.error("Error pinging InfluxDB server.");
        }

        return influxDB;
    }

    @Bean
    public InfluxDBResultMapper influxDBResultMapper() {
        return new InfluxDBResultMapper();
    }

}

