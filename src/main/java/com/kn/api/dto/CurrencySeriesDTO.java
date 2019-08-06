package com.kn.api.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CurrencySeriesDTO {

    @Setter(AccessLevel.NONE)
    private final Map<String, Float> historicalDateMap = new HashMap<>();

    @JsonAnySetter
    public void addHistoricalValue(String date, Float value) {
        this.historicalDateMap.put(date, value);
    }
}
