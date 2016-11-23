package com.newstandards.tsesna.colvir;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class Endpoint {

    private final BIProcessor biProcessor;

    public Endpoint(@Qualifier("colvirService") BIProcessor biProcessor) {
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
