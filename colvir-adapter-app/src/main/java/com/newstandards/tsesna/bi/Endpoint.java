package com.newstandards.tsesna.bi;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Example of a client of {@link BIProcessor} interface; the endpoint methods can be called like:
 *
 * <pre>
 *     http://localhost:8080/kbkList
 *     http://localhost:8080/vinCodes?bin=123456
 * </pre>
 */
@Profile("messaging-out")
@RestController
public class Endpoint {

    private final BIProcessor biProcessor;

    public Endpoint(@Qualifier("messagingBiProcessor") BIProcessor biProcessor) {
        this.biProcessor = biProcessor;
    }

    @RequestMapping("/vinCodes")
    public Map<String, Object> getVinCodes(@RequestParam String bin) {
        String vinCodes = biProcessor.getVinCodes(bin);
        return Collections.singletonMap("vinCodes", vinCodes);
    }

    @RequestMapping("/kbkList")
    public Map<String, Object> getKbkList() {
        List<Object> response = biProcessor.getKbkList();
        return Collections.singletonMap("kbkList", response);
    }
}
