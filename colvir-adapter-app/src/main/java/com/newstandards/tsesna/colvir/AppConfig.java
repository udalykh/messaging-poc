package com.newstandards.tsesna.colvir;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.jms.JmsOutboundGateway;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.messaging.MessageChannel;

import javax.jms.ConnectionFactory;


@EnableIntegration
@IntegrationComponentScan
@Configuration
public class AppConfig {

    @Bean
    public MessageChannel requests() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel="requests")
    public JmsOutboundGateway jmsGateway() {
        JmsOutboundGateway gw = new JmsOutboundGateway();
        gw.setConnectionFactory(connectionFactory());
        gw.setRequestDestinationName("test.out");
        gw.setReplyDestinationName("test.in");
        gw.setCorrelationKey("JMSCorrelationID");
        return gw;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false"));
    }

    @Bean
    public DefaultMessageListenerContainer responder() {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setDestinationName("test.out");
        MessageListenerAdapter adapter = new MessageListenerAdapter(new Object() {

            @SuppressWarnings("unused")
            public String handleMessage(String in) {
                return in.toUpperCase();
            }

        });
        container.setMessageListener(adapter);
        return container;
    }
}