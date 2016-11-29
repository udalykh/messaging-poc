package com.newstandards.tsesna.bi.colvir;

import com.newstandards.tsesna.bi.BIProcessor;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import java.util.Collections;
import java.util.List;

/**
 * Serves as a Message Endpoint - methods of this class are indirectly called by Spring Integration framework.
 * Created by default unless {@code bi-producer} Spring profile is active.
 */
@MessageEndpoint
@Profile("!bi-producer")
class ColvirBiProcessor implements BIProcessor {

    /**
     * A message is routed to this method if the message header "method" equals to "vinCodesChannel"
     */
    @ServiceActivator(inputChannel = "vinCodesChannel")
    @Override
    public String getVinCodes(String bin) {

        //TODO: do SOAP call to Colvir
        return "VIN codes: " + bin ;
    }

    /**
     * A message is routed to this method if the message header "method" equals to "kbkListChannel"
     */
    @ServiceActivator(inputChannel = "kbkListChannel")
    @Override
    public List<Object> getKbkList() {

        //TODO: do SOAP call to Colvir
        return Collections.singletonList("KBK List");
    }
}
