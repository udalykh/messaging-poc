package com.newstandards.tsesna.bi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.amqp.support.MappingUtils;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * Example of a client of {@link BIProcessor} interface; the endpoint methods can be called like:
 *
 * <pre>
 *     http://localhost:8080/kbkList
 *     http://localhost:8080/vinCodes?bin=123456
 * </pre>
 *
 * This class is created by default unless {@code bi-consumer} Spring profile is active.
 */
@Profile("!bi-consumer")
@RestController
public class Endpoint {

    @Autowired
    private MessageChannel biRequestsChannel;
    @Autowired
    private PollableChannel biReplyChannel;


    @RequestMapping("/vinCodes")
    public Map<String, Object> getVinCodes(@RequestParam String bin) {
        Message<?> message = new GenericMessage<>(bin, Collections.singletonMap(KafkaHeaders.MESSAGE_KEY, "vinCodes"));
        biRequestsChannel.send(message);

        Message<?> resp = biReplyChannel.receive(10000);
        return Collections.singletonMap("vinCodes", resp.getPayload());
    }

    @RequestMapping("/kbkList")
    public Map<String, Object> getKbkList() {
        Message<?> message = new GenericMessage<>("getKbkList", Collections.singletonMap(KafkaHeaders.MESSAGE_KEY, "kbkList"));
        biRequestsChannel.send(message);

        Message<?> resp = biReplyChannel.receive(10000);
        return Collections.singletonMap("kbkList", resp.getPayload());
    }
}
