package com.newstandards.tsesna.bi.config.kafka;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.TopicPartitionInitialOffset;
import org.springframework.messaging.MessageHandler;

@Profile("kafka")
public class KafkaMessagingBiInConfig {

    @Value("${bi.messaging.requestDestination}")
    private String requestDestination;

    @Value("${bi.messaging.replyDestination}")
    private String replyDestination;

    @Value("${kafka.messageKeyReply}")
    private String messageKeyReply;

    @Bean("requestContainer")
    public KafkaMessageListenerContainer<String, String> requestContainer(ConsumerFactory<String, String> consumerFactory) {
        return new KafkaMessageListenerContainer<>(consumerFactory,
                                                   new ContainerProperties(new TopicPartitionInitialOffset(requestDestination, 0)));
    }

    // Re-directs to vinCodesChannel in ColvirBiProcessor
    @Bean("requestAdapter")
    public KafkaMessageDrivenChannelAdapter<String, String> requestAdapter(
            @Qualifier("requestContainer") KafkaMessageListenerContainer<String, String> requestContainer) {
        KafkaMessageDrivenChannelAdapter<String, String> kafkaMessageDrivenChannelAdapter =
                new KafkaMessageDrivenChannelAdapter<>(requestContainer);
//        kafkaMessageDrivenChannelAdapter.setOutputChannelName("biResponseChannel");
        kafkaMessageDrivenChannelAdapter.setOutputChannelName("vinCodesChannel");
        return kafkaMessageDrivenChannelAdapter;
    }

    // Serves the response of ColvirBiProcessor method calls
    @ServiceActivator(inputChannel = "vinCodesChannelReply")
    @Bean
    public MessageHandler responseHandler(KafkaTemplate<String, String> biInKafkaTemplate) {
        KafkaProducerMessageHandler<String, String> handler = new KafkaProducerMessageHandler<>(biInKafkaTemplate);
        handler.setTopicExpression(new LiteralExpression(this.replyDestination));
        handler.setMessageKeyExpression(new LiteralExpression(this.messageKeyReply));
        return handler;
    }

    @Bean
    public KafkaTemplate<String, String> biInKafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public TopicCreator requestDestinationTopicCreator(@Value("${zookeeperURL}") String zookeeperURL) {
        return new TopicCreator(zookeeperURL, this.replyDestination);
    }
}
