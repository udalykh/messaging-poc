package com.newstandards.tsesna.bi.config;

import com.newstandards.tsesna.bi.config.kafka.KafkaMessagingBiOutConfig;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

/**
 * The configuration class for importing XML configuration file for Outbound Gateway.
 *
 * If this class is found on the class-path then outbound messaging-based BI mechanism is turned on
 * automatically by default unless {@code bi-consumer} Spring profile is active.
 */
@Profile("!bi-consumer")
@ImportResource({
    "classpath:com/newstandards/tsesna/bi/messaging-bi-common-producer-config.xml",
    "classpath:com/newstandards/tsesna/bi/messaging-bi-activemq-producer-config.xml",
    "classpath:com/newstandards/tsesna/bi/messaging-bi-rabbitmq-producer-config.xml"
})
@Import(KafkaMessagingBiOutConfig.class)
public class MessagingBiOutConfig {
}
