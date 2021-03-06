package com.newstandards.tsesna.bi.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.util.StringUtils;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.net.URI;
import java.net.URL;

@Configuration
@IntegrationComponentScan
@Import({MessagingBiInConfig.class, MessagingBiOutConfig.class})
public class MessagingBiConfig {

    private static Logger logger = LoggerFactory.getLogger(MessagingBiConfig.class);

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

    @Bean("ibmConnectionFactory")
    @Profile("ibmmq")
    public ConnectionFactory connectionFactory(@Value("${brokerURL}") URL brokerURL, @Value("${ibmmq.wmqChannel}") String wmqChannel) throws JMSException {
        logger.info("Creating IBM MQ connection factory with broker URL: {}", brokerURL);

        JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
        JmsConnectionFactory cf = ff.createConnectionFactory();
        cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, "QM1");
        cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
        cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, brokerURL.getHost());
        cf.setStringProperty(WMQConstants.WMQ_CHANNEL, wmqChannel);
        cf.setIntProperty(WMQConstants.WMQ_PORT, brokerURL.getPort());

        return cf;
    }
}
