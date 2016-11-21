package com.newstandards.tsesna.colvir;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class Endpoint {

    private final ColvirService colvirService;

    public Endpoint(ColvirService colvirService) {
        this.colvirService = colvirService;
    }

    @RequestMapping("/handle")
    public Map<String, String> handle(@RequestParam String request) {
        String response = colvirService.getVersion(request);
        return Collections.singletonMap("response", response);
    }
}
