package com.kn.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CurrentPriceDTO {

    @JsonProperty(value="time")
    private TimeDTO TimeObject;

    @JsonProperty(value="bpi")
    private CurrenciesDTO currenciesDTO;
}
