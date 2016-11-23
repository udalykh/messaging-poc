package com.newstandards.tsesna.colvir;

import java.util.List;

public interface BIProcessor {

    /**
     * Getting the list of VIN codes by IIN/BIN
     *
     * @param bin code
     * @return
     */
    String getVinCodes(String bin);



    List<Object> getKbkList();
}
