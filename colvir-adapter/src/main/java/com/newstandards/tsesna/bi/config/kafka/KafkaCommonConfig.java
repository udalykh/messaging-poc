package com.newstandards.tsesna.bi.config.kafka;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Profile("kafka")
public class KafkaCommonConfig {

    @Bean
    public ProducerFactory<String, String> producerFactory(@Qualifier("kafkaProducerConfig") KafkaProducerConfig config,
                                                           @Value("${brokerURL}") String brokerURL) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerURL);
        props.put(ProducerConfig.RETRIES_CONFIG, config.getRetriesConfig());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, config.getBatchSizeConfig());
        props.put(ProducerConfig.LINGER_MS_CONFIG, config.getLingerMsConfig());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, config.getBufferMemoryConfig());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory(@Qualifier("kafkaConsumerConfig") KafkaConsumerConfig config,
                                                           @Value("${brokerURL}") String brokerURL) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerURL);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.getGroupIdConfig());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, config.isEnableAutoCommitConfig());
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, config.getAutoCommitIntervalMsConfig());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, config.getSessionTimeoutMsConfig());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }
}
