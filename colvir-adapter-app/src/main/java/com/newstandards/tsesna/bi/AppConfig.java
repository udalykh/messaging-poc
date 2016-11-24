package com.newstandards.tsesna.bi;

import org.apache.activemq.ActiveMQConnectionFactory;
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

    /**
     * JMS connection factory based on internal Apache Active MQ message broker
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false"));
    }
}
