package com.newstandards.tsesna.bi.config.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("kafka")
public class KafkaConsumerConfig {

    @Value("${kafka.consumer.groupIdConfig}")
    private String groupIdConfig;
    @Value("${kafka.consumer.enableAutoCommitConfig}")
    private boolean enableAutoCommitConfig;
    @Value("${kafka.consumer.autoCommitIntervalMsConfig}")
    private int autoCommitIntervalMsConfig;
    @Value("${kafka.consumer.sessionTimeoutMsConfig}")
    private int sessionTimeoutMsConfig;

    String getGroupIdConfig() {
        return groupIdConfig;
    }

    boolean isEnableAutoCommitConfig() {
        return enableAutoCommitConfig;
    }

    int getAutoCommitIntervalMsConfig() {
        return autoCommitIntervalMsConfig;
    }

    int getSessionTimeoutMsConfig() {
        return sessionTimeoutMsConfig;
    }
}
