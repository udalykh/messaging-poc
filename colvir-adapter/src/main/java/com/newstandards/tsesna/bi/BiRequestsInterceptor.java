package com.newstandards.tsesna.bi;

import org.springframework.context.annotation.Profile;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * Serves an BI messaging interceptor before sending a message; some extra headers can be added here (for example the security token)
 * Created by default unless {@code bi-consumer} Spring profile is active.
 */
@Component("biRequestsInterceptor")
@Profile("!bi-consumer")
class BiRequestsInterceptor extends ChannelInterceptorAdapter {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        MutableMessageHeaders headers = new MutableMessageHeaders(message.getHeaders());
        // TODO: add a security token as a header to "headers"

        return new GenericMessage(message.getPayload(), headers);
    }
}
