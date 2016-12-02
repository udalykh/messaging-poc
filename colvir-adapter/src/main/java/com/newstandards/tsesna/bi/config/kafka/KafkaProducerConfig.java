package com.newstandards.tsesna.bi.config.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("kafka")
public class KafkaProducerConfig {

    @Value("${kafka.producer.retriesConfig}")
    private int retriesConfig;
    @Value("${kafka.producer.batchSizeConfig}")
    private int batchSizeConfig;
    @Value("${kafka.producer.lingerMsConfig}")
    private int lingerMsConfig;
    @Value("${kafka.producer.bufferMemoryConfig}")
    private int bufferMemoryConfig;

    public int getRetriesConfig() {
        return retriesConfig;
    }

    public int getBatchSizeConfig() {
        return batchSizeConfig;
    }

    public int getLingerMsConfig() {
        return lingerMsConfig;
    }

    public int getBufferMemoryConfig() {
        return bufferMemoryConfig;
    }
}
