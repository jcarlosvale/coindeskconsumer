package com.kn.api.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CurrenciesDTO {

    @Setter(AccessLevel.NONE)
    private final Map<String, CurrencyDTO> currencies = new HashMap<>();

    @JsonAnySetter
    public void addCurrencyInfo(String currencyCode, CurrencyDTO value) {
        this.currencies.put(currencyCode, value);
    }
}
