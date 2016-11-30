package com.newstandards.tsesna.bi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootApplication
@ContextConfiguration(
        classes = {AppConfig.class, MessagingBiInConfig.class, MessagingBiOutConfig.class},
        initializers = ConfigFileApplicationContextInitializer.class
)
public class MessagingBiTest {

    @Autowired
    @Qualifier("messagingBiProcessor")
    private BIProcessor biProcessor;

    @Test
    public void shouldGetVinCodes() {
        String codes = biProcessor.getVinCodes("12345");
        assertThat(codes).isEqualTo("VIN codes: 12345");
    }

    @Test
    public void shouldGetKbkList() {
        List<Object> kbkList =  biProcessor.getKbkList();
        assertThat(kbkList).isEqualTo(Collections.singletonList("KBK List"));
    }
}
