package com.unloadbrain.kafka.connect.influxdb.prop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.core.env.Environment;

@Getter
@Builder
@AllArgsConstructor
public class KafkaConnectInfluxDBSinkPropertiesReader {

    private static final String KEY_NOT_DEFINED_FORMAT = "%s is not defined.";

    private static final String ENV_KEY_KAFKA_BOOTSTRAP_SERVERS = "kafka.connector.bootstrap.servers";
    private static final String ENV_KEY_KAFKA_CONSUMER_GROUP_ID = "kafka.connector.consumer.groupId";
    private static final String ENV_KEY_KAFKA_CONSUMER_AUTO_OFFSET_RESET = "kafka.connector.consumer.autoOffsetReset";
    private static final String ENV_KEY_KAFKA_TOPICS = "kafka.connector.topics";
    private static final String ENV_KEY_INFLUXDB_URL = "kafka.connector.influxdb.url";
    private static final String ENV_KEY_INFLUXDB_USERNAME = "kafka.connector.influxdb.username";
    private static final String ENV_KEY_INFLUXDB_PASSWORD = "kafka.connector.influxdb.password";
    private static final String ENV_KEY_INFLUXDB_DATABASE = "kafka.connector.influxdb.database";
    private static final String ENV_KEY_INFLUXDB_RETENTION_POLICY = "kafka.connector.influxdb.retentionPolicy";
    private static final String ENV_KEY_INFLUXDB_READ_TIMEOUT_MS = "kafka.connector.influxdb.readTimeOut";

    private static final int DEFAULT_ENV_KEY_INFLUXDB_READ_TIMEOUT_MS = 3000;

    private final Environment environment;

    public KafkaConnectInfluxDBSinkProperties read() {

        String kafkaBootstrapServers = environment.getProperty(ENV_KEY_KAFKA_BOOTSTRAP_SERVERS);
        if (kafkaBootstrapServers == null) {
            throw new IllegalStateException(String.format(KEY_NOT_DEFINED_FORMAT, ENV_KEY_KAFKA_BOOTSTRAP_SERVERS));
        }

        String kafkaConsumerGroupId = environment.getProperty(ENV_KEY_KAFKA_CONSUMER_GROUP_ID);
        if (kafkaConsumerGroupId == null) {
            throw new IllegalStateException(String.format(KEY_NOT_DEFINED_FORMAT, ENV_KEY_KAFKA_CONSUMER_GROUP_ID));
        }

        String kafkaConsumerAutoOffsetReset = environment.getProperty(ENV_KEY_KAFKA_CONSUMER_AUTO_OFFSET_RESET);
        if (kafkaConsumerAutoOffsetReset == null) {
            throw new IllegalStateException(String.format(KEY_NOT_DEFINED_FORMAT, ENV_KEY_KAFKA_CONSUMER_AUTO_OFFSET_RESET));
        }

        String kafkaTopics = environment.getProperty(ENV_KEY_KAFKA_TOPICS);
        if (kafkaTopics == null) {
            throw new IllegalStateException(String.format(KEY_NOT_DEFINED_FORMAT, ENV_KEY_KAFKA_TOPICS));
        }

        String influxDbUrl = environment.getProperty(ENV_KEY_INFLUXDB_URL);
        if (influxDbUrl == null) {
            throw new IllegalStateException(String.format(KEY_NOT_DEFINED_FORMAT, ENV_KEY_INFLUXDB_URL));
        }

        String influxDbUsername = environment.getProperty(ENV_KEY_INFLUXDB_USERNAME);
        if (influxDbUsername == null) {
            throw new IllegalStateException(String.format(KEY_NOT_DEFINED_FORMAT, ENV_KEY_INFLUXDB_USERNAME));
        }

        String influxDbPassword = environment.getProperty(ENV_KEY_INFLUXDB_PASSWORD);
        if (influxDbPassword == null) {
            throw new IllegalStateException(String.format(KEY_NOT_DEFINED_FORMAT, ENV_KEY_INFLUXDB_PASSWORD));
        }

        String influxDbDatabase = environment.getProperty(ENV_KEY_INFLUXDB_DATABASE);
        if (influxDbDatabase == null) {
            throw new IllegalStateException(String.format(KEY_NOT_DEFINED_FORMAT, ENV_KEY_INFLUXDB_DATABASE));
        }

        String influxDbRetentionPolicy = environment.getProperty(ENV_KEY_INFLUXDB_RETENTION_POLICY);
        if (influxDbRetentionPolicy == null) {
            throw new IllegalStateException(String.format(KEY_NOT_DEFINED_FORMAT, ENV_KEY_INFLUXDB_RETENTION_POLICY));
        }

        String influxDbReadTimeoutInMsString = environment.getProperty(ENV_KEY_INFLUXDB_READ_TIMEOUT_MS);
        int influxDbReadTimeoutInMS = influxDbReadTimeoutInMsString == null ? DEFAULT_ENV_KEY_INFLUXDB_READ_TIMEOUT_MS : Integer.parseInt(influxDbReadTimeoutInMsString);

        return KafkaConnectInfluxDBSinkProperties.builder()
                .kafkaBootstrapServers(kafkaBootstrapServers)
                .kafkaConsumerGroupId(kafkaConsumerGroupId)
                .kafkaConsumerAutoOffsetReset(kafkaConsumerAutoOffsetReset)
                .kafkaTopics(kafkaTopics)
                .influxDbUrl(influxDbUrl)
                .influxDbUsername(influxDbUsername)
                .influxDbPassword(influxDbPassword)
                .influxDbDatabase(influxDbDatabase)
                .influxDbRetentionPolicy(influxDbRetentionPolicy)
                .influxDbReadTimeoutInMs(influxDbReadTimeoutInMS)
                .build();

    }
}
