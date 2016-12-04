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
public class KafkaMessagingBiOutConfig {

    @Value("${bi.messaging.requestDestination}")
    private String requestDestination;

    @Value("${bi.messaging.replyDestination}")
    private String replyDestination;

    @Value("${kafka.messageKeyRequest}")
    private String messageKeyRequest;

    // handles calls to MessagingBiProcessor sending a message to request destination
    @ServiceActivator(inputChannel = "biRequestsChannel")
    @Bean
    public MessageHandler requestHandler(KafkaTemplate<String, String> biOutKafkaTemplate) {
        KafkaProducerMessageHandler<String, String> handler = new KafkaProducerMessageHandler<>(biOutKafkaTemplate);
        handler.setTopicExpression(new LiteralExpression(this.requestDestination));
//        handler.setSendTimeout(); //TODO
        return handler;
    }

    @Bean
    public KafkaTemplate<String, String> biOutKafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }



    @Bean("replyContainer")
    public KafkaMessageListenerContainer<String, String> replyContainer(ConsumerFactory<String, String> consumerFactory)
            throws NoSuchMethodException {
        ContainerProperties properties = new ContainerProperties(new TopicPartitionInitialOffset(replyDestination, 0));
        return new KafkaMessageListenerContainer<>(consumerFactory, properties);
    }


    @Bean("responseAdapter")
    public KafkaMessageDrivenChannelAdapter<String, String> responseAdapter(@Qualifier("replyContainer")
            KafkaMessageListenerContainer<String, String> replyContainer) {
        KafkaMessageDrivenChannelAdapter<String, String> kafkaMessageDrivenChannelAdapter =
                new KafkaMessageDrivenChannelAdapter<>(replyContainer);
        kafkaMessageDrivenChannelAdapter.setOutputChannelName("biReplyChannel");
        return kafkaMessageDrivenChannelAdapter;
    }

    @Bean
    public TopicCreator requestDestinationTopicCreator(@Value("${zookeeperURL}") String zookeeperURL) {
        return new TopicCreator(zookeeperURL, this.requestDestination);
    }
}
