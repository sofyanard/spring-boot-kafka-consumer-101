package com.sofyanard.kafka;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("consumer")
public class ConsumerApp implements CommandLineRunner {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${kafka.key-deserializer}")
    private String keyDeserializer;

    @Value("${kafka.value-deserializer}")
    private String valueDeserializer;

    @Value("${kafka.consumer-group-id}")
    private String consumerGroupId;

    @Value("${kafka.security.protocol}")
    private String securityProtocol;

    @Value("${kafka.sasl.mechanism}")
    private String saslMechanism;

    @Value("${kafka.sasl.username}")
    private String saslUsername;

    @Value("${kafka.sasl.password}")
    private String saslPassword;

    @Value("${kafka.topic}")
    private String topic;

    @Override
    public void run(String... args) throws Exception {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        properties.put("security.protocol", securityProtocol);
        properties.put("sasl.mechanism", saslMechanism);
        properties.put(
                "sasl.jaas.config",
                String.format(
                        "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";",
                        saslUsername,
                        saslPassword));

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of(topic));

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
