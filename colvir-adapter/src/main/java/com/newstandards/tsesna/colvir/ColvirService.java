package com.newstandards.tsesna.colvir;


import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(name = "colvirService", defaultRequestChannel = "requests")
public interface ColvirService {

    String getVersion(String dummy);
}
