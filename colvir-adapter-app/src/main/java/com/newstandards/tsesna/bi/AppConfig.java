package com.newstandards.tsesna.bi;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;

/**
 * Simplistic app configuration creating an embedded JMS connection factory.
 */
@EnableIntegration
@IntegrationComponentScan
@Configuration
public class AppConfig {

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    /**
     * JMS connection factory based on internal Apache Active MQ message broker
     */
    @Bean
    public ConnectionFactory connectionFactory(@Value("${brokerURL}") String brokerURL) {
        logger.info("Creating connection factory with broker URL: {}", brokerURL);
        return new CachingConnectionFactory(new ActiveMQConnectionFactory(brokerURL));
    }
}
