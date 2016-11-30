package com.newstandards.tsesna.bi;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.util.StringUtils;

import javax.jms.ConnectionFactory;
import java.net.URI;

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
    @Bean("connectionFactory")
    @Profile({"default", "activemq"})
    public ConnectionFactory jmsConnectionFactory(@Value("${brokerURL}") String brokerURL) {
        logger.info("Creating JMS connection factory with broker URL: {}", brokerURL);
        return new CachingConnectionFactory(new ActiveMQConnectionFactory(brokerURL));
    }

    /**
     * AMQP connection factory based on Rabbit MQ message broker
     */
    @Bean("rabbitConnectionFactory")
    @Profile("rabbitmq")
    public org.springframework.amqp.rabbit.connection.ConnectionFactory amqpConnectionFactory(@Value("${brokerURL}") String brokerURL) {
        if (StringUtils.hasText(brokerURL)) {
            logger.info("Creating AMQP connection factory with broker URL: {}", brokerURL);
            return new org.springframework.amqp.rabbit.connection.CachingConnectionFactory(URI.create(brokerURL));
        } else {
            logger.info("Creating AMQP connection factory with default broker URL");
            return new org.springframework.amqp.rabbit.connection.CachingConnectionFactory();
        }
    }
}
