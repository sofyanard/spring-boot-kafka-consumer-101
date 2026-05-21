package com.sofyanard.kafka;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class ConsumerApp {
    public static void main(String[] args) {
        System.out.println("Hello World");

        Properties appProps = new Properties();
        try (var input = ConsumerApp.class.getClassLoader().getResourceAsStream("application.properties")) {
            appProps.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, appProps.getProperty("kafka.bootstrap-servers"));
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, appProps.getProperty("kafka.auto-offset-reset"));
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, appProps.getProperty("kafka.key-deserializer"));
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                appProps.getProperty("kafka.value-deserializer"));
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, appProps.getProperty("kafka.group-id"));

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of("thermostat_readings"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> message : records) {
                System.out.println("Key: " + message.key());
                System.out.println("Value: " + message.value());
                System.out.println("Partition: " + message.partition());
                System.out.println("Offset: " + message.offset());
            }
        }
    }
}
