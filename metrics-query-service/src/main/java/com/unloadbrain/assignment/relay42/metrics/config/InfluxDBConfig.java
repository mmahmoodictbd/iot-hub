package com.unloadbrain.assignment.relay42.metrics.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * This class provides InfluxDB related configuration and beans.
 */
@Slf4j
@Configuration
public class InfluxDBConfig {

    private final String url;
    private final String username;
    private final String password;
    private final String database;
    private final String retentionPolicy;
    private final long readTimeoutMs;

    public InfluxDBConfig(@Value("${metrics.influxdb.url}") String url,
                          @Value("${metrics.influxdb.username}") String username,
                          @Value("${metrics.influxdb.password}") String password,
                          @Value("${metrics.influxdb.database}") String database,
                          @Value("${metrics.influxdb.retention-policy}") String retentionPolicy,
                          @Value("${metrics.influxdb.read-timeout}") long readTimeoutMs) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.database = database;
        this.retentionPolicy = retentionPolicy;
        this.readTimeoutMs = readTimeoutMs;
    }

    @Bean
    public InfluxDB influxDB() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder().readTimeout(readTimeoutMs, TimeUnit.MILLISECONDS);
        InfluxDB influxDB = InfluxDBFactory.connect(url, username, password, builder);

        influxDB.query(new Query(String.format("CREATE DATABASE %s", database), database));
        influxDB.setDatabase(database);

        influxDB.setRetentionPolicy(retentionPolicy);
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