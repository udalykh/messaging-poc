package com.newstandards.tsesna.bi;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

/**
 * The configuration class for importing XML configuration file for Outbound Gateway.
 *
 * If this class is found on the class-path then outbound messaging-based BI mechanism is turned on
 * automatically if "messaging-out" Spring profile is active.
 */
@Configuration
@Profile("messaging-out")
@ImportResource("classpath:com/newstandards/tsesna/bi/messaging-bi-config-out.xml")
public class MessagingBiOutConfig {
}
