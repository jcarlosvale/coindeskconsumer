package com.kn.api.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import java.util.TreeMap;

/**
 * Domain Class representing the Historical Price
 * The values are stored in a TreeSet, a sorted MAP, it makes more easy to retrieve the highest/lowest value
 */
@EqualsAndHashCode
@ToString
public class HistoricalPrice {

    @Getter
    private final String code;

    private final TreeMap<Float, String> mapValues = new TreeMap<>();

    public HistoricalPrice(String currencyCode) {
        this.code = currencyCode;
    }

    public void add(String date, Float value) {
        mapValues.put(value,date);
    }

    /**
     *
     * @return The highest value <Float> and date <String>
     */
    public Map.Entry<Float, String> getHighestBitCoinRate() {
        return mapValues.lastEntry();
    }

    /**
     *
     * @return The lowest value <Float> and date <String>
     */
    public Map.Entry<Float, String> getLowestBitCoinRate() {
        return mapValues.firstEntry();
    }
}
