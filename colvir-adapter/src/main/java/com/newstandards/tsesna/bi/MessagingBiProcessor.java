package com.newstandards.tsesna.bi;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.List;

/**
 * Provides an Integration Messaging Gateway Proxy for {@link BIProcessor} unaware of the message endpoint implementation. Spring Integration
 * framework creates a messaging-based implementation of this interface.
 *
 * This interface has package visibility and is not supposed to be used directly in classes; {@link BIProcessor} interface should be injected as a
 * Spring bean.
 *
 * TODO: one of the parameters should be some security token to be analyzed by the recipient
 */
@MessagingGateway(name = "messagingBiProcessor", defaultRequestChannel = "biRequestsChannel", defaultReplyChannel = "biReplyChannel")
interface MessagingBiProcessor extends BIProcessor {

    /**
     * Gateway headers are needed for proper routing of the messages for different gateway methods
     */
    @Gateway(headers = {@GatewayHeader(name = "method", value = "vinCodes")})
    @Override
    String getVinCodes(String bin);

    /**
     * Methods without parameters have to have some payload;
     * @see <a href="http://docs.spring.io/spring-integration/reference/html/messaging-endpoints-chapter.html#gateway-calling-no-argument-methods">
     * Invoking No-Argument Methods</a>
     * TODO: replace with the security token
     */
    @Override
    @Payload("#gatewayMethod.name")
    @Gateway(headers = {@GatewayHeader(name = "method", value = "kbkList")})
    List<Object> getKbkList();
}

