package com.newstandards.tsesna.bi;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * The configuration class for importing XML configuration file.
 * If this class is found on the class-path then messaging-based BI mechanism is turned on automatically.
 */
@Configuration
@ImportResource("classpath:com/newstandards/tsesna/bi/messaging-bi-config.xml")
public class MessagingBiConfig {
}
