package com.kn.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Main DTO representing the Current Price JSON
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentPriceDTO {

    @JsonProperty(value="time")
    private TimeDTO timeDTO;

    @JsonProperty(value="bpi")
    private CurrenciesDTO currenciesDTO;
}
