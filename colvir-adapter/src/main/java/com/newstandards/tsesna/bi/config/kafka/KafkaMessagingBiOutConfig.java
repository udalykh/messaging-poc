package com.newstandards.tsesna.bi.config.kafka;

import org.I0Itec.zkclient.ZkClient;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.messaging.MessageHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import kafka.admin.AdminUtils;
import kafka.common.TopicExistsException;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;

@Configuration
@Profile("kafka")
public class KafkaMessagingBiOutConfig {

    @Value("${bi.messaging.requestDestination}")
    private String requestDestination;

    @Value("${kafka.messageKey}")
    private String messageKey;

    @ServiceActivator(inputChannel = "toKafka")
    @Bean
    public MessageHandler handler(KafkaTemplate<String, String> kafkaTemplate) throws Exception {
        KafkaProducerMessageHandler<String, String> handler = new KafkaProducerMessageHandler<>(kafkaTemplate);
        handler.setTopicExpression(new LiteralExpression(this.requestDestination));
        handler.setMessageKeyExpression(new LiteralExpression(this.messageKey));
        return handler;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, String> producerFactory(KafkaProducerConfig kafkaProducerConfig, @Value("${brokerURL}") String brokerURL) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerURL);
        props.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerConfig.getRetriesConfig());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProducerConfig.getBatchSizeConfig());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerConfig.getLingerMsConfig());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaProducerConfig.getBufferMemoryConfig());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }


    @Bean
    public TopicCreator requestDestinationTopicCreator(@Value("${zookeeperURL}") String zookeeperURL) {
        return new TopicCreator(this.requestDestination, zookeeperURL);
    }

    public static class TopicCreator implements SmartLifecycle {

        private final String topic;

        private final String zkConnect;

        private volatile boolean running;

        public TopicCreator(String topic, String zkConnect) {
            this.topic = topic;
            this.zkConnect = zkConnect;
        }

        @Override
        public void start() {
            ZkUtils zkUtils = new ZkUtils(new ZkClient(this.zkConnect, 6000, 6000,
                                                       ZKStringSerializer$.MODULE$), null, false);
            try {
                AdminUtils.createTopic(zkUtils, topic, 1, 1, new Properties(), null);
            }
            catch (TopicExistsException e) {
                // no-op
            }
            this.running = true;
        }

        @Override
        public void stop() {
        }

        @Override
        public boolean isRunning() {
            return this.running;
        }

        @Override
        public int getPhase() {
            return Integer.MIN_VALUE;
        }

        @Override
        public boolean isAutoStartup() {
            return true;
        }

        @Override
        public void stop(Runnable callback) {
            callback.run();
        }

    }
}
