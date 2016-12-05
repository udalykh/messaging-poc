package com.newstandards.tsesna.bi;

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
    String getKbkList();
}
