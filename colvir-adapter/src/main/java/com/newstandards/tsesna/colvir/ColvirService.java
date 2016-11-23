package com.newstandards.tsesna.colvir;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.List;

/**
 * Service exposing Colvir methods
 */
@SuppressWarnings("UnresolvedMessageChannel")
@MessagingGateway(name = "colvirService", defaultRequestChannel = "colvirRequestsChannel", defaultReplyChannel = "colvirReplyChannel")
public interface ColvirService extends BIProcessor {

    @Override
    @Payload("#gatewayMethod.name")
    @Gateway(requestChannel = "kbkListChannel")
    List<Object> getKbkList();

//    @Gateway(requestChannel = "demoChannel")
    @Gateway
    @Override
    String getVinCodes(String bin);
}
