/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.newstandards.tsesna.colvir;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import java.util.Collections;
import java.util.List;

/**
 */
@SuppressWarnings("UnresolvedMessageChannel")
@MessageEndpoint("colvirSoapConnector" )

/*

    <jms:inbound-gateway request-destination-name="${colvir.requestDestination}"
                         request-channel="demoChannel"/>
 */
public class ColvirSoapConnector/* implements BIProcessor */{

    /**
     * Getting the list of VIN codes by IIN/BIN
     *
     * @param bin code
     * @return
     */
    @ServiceActivator(inputChannel = "demoChannel")
//    @Override
    public String getVinCodes(String bin) {
        return "VIN codes";
    }

//    @ServiceActivator(inputChannel = "kbkListChannel")
    @ServiceActivator(inputChannel = "demoChannel")
//    @Override
    public List<Object> getKbkList() {
        return Collections.singletonList("KBK List");
    }
}
