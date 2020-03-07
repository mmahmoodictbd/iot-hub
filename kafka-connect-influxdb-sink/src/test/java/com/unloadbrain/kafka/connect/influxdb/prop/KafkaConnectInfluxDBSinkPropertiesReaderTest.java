package com.unloadbrain.kafka.connect.influxdb.prop;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.mock.env.MockEnvironment;

import static org.junit.Assert.assertEquals;

public class KafkaConnectInfluxDBSinkPropertiesReaderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldBuildKafkaConnectInfluxDBSinkPropertiesFromEnvironmentProperties() {

        // Given

        MockEnvironment environment = new MockEnvironment()
                .withProperty("kafka.connector.bootstrap.servers", "localhost:9092")
                .withProperty("kafka.connector.consumer.groupId", "exampleGroupId")
                .withProperty("kafka.connector.consumer.autoOffsetReset", "earliest")
                .withProperty("kafka.connector.topics", "ExampleTopic")
                .withProperty("kafka.connector.influxdb.url", "http://localhost:8086")
                .withProperty("kafka.connector.influxdb.username", "admin")
                .withProperty("kafka.connector.influxdb.password", "admin")
                .withProperty("kafka.connector.influxdb.database", "db0")
                .withProperty("kafka.connector.influxdb.retentionPolicy", "autogen")
                .withProperty("kafka.connector.influxdb.readTimeOut", "3000");

        // When
        KafkaConnectInfluxDBSinkProperties properties = new KafkaConnectInfluxDBSinkPropertiesReader(environment).read();

        // Then
        assertEquals("localhost:9092", properties.getKafkaBootstrapServers());
        assertEquals("exampleGroupId", properties.getKafkaConsumerGroupId());
        assertEquals("earliest", properties.getKafkaConsumerAutoOffsetReset());
        assertEquals("ExampleTopic", properties.getKafkaTopics());
        assertEquals("http://localhost:8086", properties.getInfluxDbUrl());
        assertEquals("admin", properties.getInfluxDbUsername());
        assertEquals("admin", properties.getInfluxDbPassword());
        assertEquals("db0", properties.getInfluxDbDatabase());
        assertEquals("autogen", properties.getInfluxDbRetentionPolicy());
        assertEquals(3000, properties.getInfluxDbReadTimeoutInMs());
    }

    @Test
    public void shouldBuildKafkaConnectInfluxDBSinkPropertiesWithDefaultdReadTimeoutMs() {

        // Given

        MockEnvironment environment = new MockEnvironment()
                .withProperty("kafka.connector.bootstrap.servers", "localhost:9092")
                .withProperty("kafka.connector.consumer.groupId", "exampleGroupId")
                .withProperty("kafka.connector.consumer.autoOffsetReset", "earliest")
                .withProperty("kafka.connector.topics", "ExampleTopic")
                .withProperty("kafka.connector.influxdb.url", "http://localhost:8086")
                .withProperty("kafka.connector.influxdb.username", "admin")
                .withProperty("kafka.connector.influxdb.password", "admin")
                .withProperty("kafka.connector.influxdb.database", "db0")
                .withProperty("kafka.connector.influxdb.retentionPolicy", "autogen");

        // When
        KafkaConnectInfluxDBSinkProperties properties = new KafkaConnectInfluxDBSinkPropertiesReader(environment).read();

        // Then
        assertEquals(3000, properties.getInfluxDbReadTimeoutInMs());
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfBootstrapServersNotSet() {

        // Given

        MockEnvironment environment = new MockEnvironment();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("kafka.connector.bootstrap.servers is not defined.");

        // When
        new KafkaConnectInfluxDBSinkPropertiesReader(environment).read();

        // Then
        // Expect test to be passed.
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfConsumerGroupIdNotSet() {

        // Given

        MockEnvironment environment = new MockEnvironment()
                .withProperty("kafka.connector.bootstrap.servers", "localhost:9092");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("kafka.connector.consumer.groupId is not defined.");

        // When
        new KafkaConnectInfluxDBSinkPropertiesReader(environment).read();

        // Then
        // Expect test to be passed.
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfConsumerAutoOffsetResetNotSet() {

        // Given

        MockEnvironment environment = new MockEnvironment()
                .withProperty("kafka.connector.bootstrap.servers", "localhost:9092")
                .withProperty("kafka.connector.consumer.groupId", "exampleGroupId");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("kafka.connector.consumer.autoOffsetReset is not defined.");

        // When
        new KafkaConnectInfluxDBSinkPropertiesReader(environment).read();

        // Then
        // Expect test to be passed.
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfTopicsNotSet() {

        // Given

        MockEnvironment environment = new MockEnvironment()
                .withProperty("kafka.connector.bootstrap.servers", "localhost:9092")
                .withProperty("kafka.connector.consumer.groupId", "exampleGroupId")
                .withProperty("kafka.connector.consumer.autoOffsetReset", "earliest");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("kafka.connector.topics is not defined.");

        // When
        new KafkaConnectInfluxDBSinkPropertiesReader(environment).read();

        // Then
        // Expect test to be passed.
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfInfluxDBUrlNotSet() {

        // Given

        MockEnvironment environment = new MockEnvironment()
                .withProperty("kafka.connector.bootstrap.servers", "localhost:9092")
                .withProperty("kafka.connector.consumer.groupId", "exampleGroupId")
                .withProperty("kafka.connector.consumer.autoOffsetReset", "earliest")
                .withProperty("kafka.connector.topics", "ExampleTopic");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("kafka.connector.influxdb.url is not defined.");

        // When
        new KafkaConnectInfluxDBSinkPropertiesReader(environment).read();

        // Then
        // Expect test to be passed.
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfInfluxDBUsernameNotSet() {

        // Given

        MockEnvironment environment = new MockEnvironment()
                .withProperty("kafka.connector.bootstrap.servers", "localhost:9092")
                .withProperty("kafka.connector.consumer.groupId", "exampleGroupId")
                .withProperty("kafka.connector.consumer.autoOffsetReset", "earliest")
                .withProperty("kafka.connector.topics", "ExampleTopic")
                .withProperty("kafka.connector.influxdb.url", "http://localhost:8086");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("kafka.connector.influxdb.username is not defined.");

        // When
        new KafkaConnectInfluxDBSinkPropertiesReader(environment).read();

        // Then
        // Expect test to be passed.
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfInfluxDBPasswordNotSet() {

        // Given

        MockEnvironment environment = new MockEnvironment()
                .withProperty("kafka.connector.bootstrap.servers", "localhost:9092")
                .withProperty("kafka.connector.consumer.groupId", "exampleGroupId")
                .withProperty("kafka.connector.consumer.autoOffsetReset", "earliest")
                .withProperty("kafka.connector.topics", "ExampleTopic")
                .withProperty("kafka.connector.influxdb.url", "http://localhost:8086")
                .withProperty("kafka.connector.influxdb.username", "admin");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("kafka.connector.influxdb.password is not defined.");

        // When
        new KafkaConnectInfluxDBSinkPropertiesReader(environment).read();

        // Then
        // Expect test to be passed.
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfInfluxDBDatabaseNotSet() {

        // Given

        MockEnvironment environment = new MockEnvironment()
                .withProperty("kafka.connector.bootstrap.servers", "localhost:9092")
                .withProperty("kafka.connector.consumer.groupId", "exampleGroupId")
                .withProperty("kafka.connector.consumer.autoOffsetReset", "earliest")
                .withProperty("kafka.connector.topics", "ExampleTopic")
                .withProperty("kafka.connector.influxdb.url", "http://localhost:8086")
                .withProperty("kafka.connector.influxdb.username", "admin")
                .withProperty("kafka.connector.influxdb.password", "admin");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("kafka.connector.influxdb.database is not defined.");

        // When
        new KafkaConnectInfluxDBSinkPropertiesReader(environment).read();

        // Then
        // Expect test to be passed.
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfInfluxDBRetentionPolicyNotSet() {

        // Given

        MockEnvironment environment = new MockEnvironment()
                .withProperty("kafka.connector.bootstrap.servers", "localhost:9092")
                .withProperty("kafka.connector.consumer.groupId", "exampleGroupId")
                .withProperty("kafka.connector.consumer.autoOffsetReset", "earliest")
                .withProperty("kafka.connector.topics", "ExampleTopic")
                .withProperty("kafka.connector.influxdb.url", "http://localhost:8086")
                .withProperty("kafka.connector.influxdb.username", "admin")
                .withProperty("kafka.connector.influxdb.password", "admin")
                .withProperty("kafka.connector.influxdb.database", "db0");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("kafka.connector.influxdb.retentionPolicy is not defined.");

        // When
        new KafkaConnectInfluxDBSinkPropertiesReader(environment).read();

        // Then
        // Expect test to be passed.
    }
}