package com.newstandards.tsesna.bi;

import java.util.List;

/**
 * Generic interface with business integration methods
 */
public interface BIProcessor {

    /**
     * Getting the list of VIN codes by IIN/BIN
     */
    String getVinCodes(String bin);

    /**
     * Gets a list of KBK (Коды бюджетной классификации)
     */
    List<Object> getKbkList();
}
