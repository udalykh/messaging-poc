package com.newstandards.tsesna.bi;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

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
    public ConnectionFactory connectionFactory(@Value("${brokerURL}") String brokerURL) throws JMSException {
        logger.info("Creating connection factory with broker URL: {}", brokerURL);

        JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
	    JmsConnectionFactory cf = ff.createConnectionFactory();
	    cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, "QM1");
	    cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
	    cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, "localhost");
	    cf.setIntProperty(WMQConstants.WMQ_PORT, 1414);

        return cf;
    }
}
