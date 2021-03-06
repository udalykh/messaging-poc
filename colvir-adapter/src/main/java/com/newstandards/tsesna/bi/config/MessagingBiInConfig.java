package com.newstandards.tsesna.bi.config;

import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

/**
 * The configuration class for importing XML configuration file for Inbound Gateway.
 *
 * If this class is found on the class-path then inbound messaging-based BI mechanism is turned on
 * automatically by default unless {@code bi-producer} Spring profile is active.
 */
@Profile("!bi-producer")
@ImportResource({
    "classpath:com/newstandards/tsesna/bi/messaging-bi-common-consumer-config.xml",
    "classpath:com/newstandards/tsesna/bi/messaging-bi-activemq-consumer-config.xml",
    "classpath:com/newstandards/tsesna/bi/messaging-bi-rabbitmq-consumer-config.xml",
})
public class MessagingBiInConfig {
}
