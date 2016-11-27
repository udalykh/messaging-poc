package com.newstandards.tsesna.bi.colvir;

import org.springframework.context.annotation.Profile;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Message selector acting supposed to be filter out invalid messages (for example with invalid security token).
 * Created by default unless {@code bi-producer} Spring profile is active.
 */
@Component("biMessageSelector")
@Profile("!bi-producer")
class ColvirMessageSelector implements MessageSelector {

    @Override
    public boolean accept(Message<?> message) {

        // TODO: implement filtering logic; allowing all the messages by default
        return true;
    }
}
