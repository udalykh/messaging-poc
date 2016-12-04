package com.newstandards.tsesna.bi.config.kafka;


import kafka.admin.AdminUtils;
import kafka.common.TopicExistsException;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

import java.util.Properties;

class TopicCreator implements SmartLifecycle {

    private static Logger logger = LoggerFactory.getLogger(TopicCreator.class);

    private final String topic;

    private final String zkConnect;

    private volatile boolean running;

    TopicCreator(String zkConnect, String topic) {
        this.topic = topic;
        this.zkConnect = zkConnect;
    }

    @Override
    public void start() {
        ZkUtils zkUtils = new ZkUtils(new ZkClient(this.zkConnect, 6000, 6000, ZKStringSerializer$.MODULE$), null, false);
        try {
            AdminUtils.createTopic(zkUtils, topic, 1, 1, new Properties(), null);
            logger.info("New topic {} created", topic);
        } catch (TopicExistsException e) {
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
