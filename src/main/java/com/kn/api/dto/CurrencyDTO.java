package com.kn.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CurrencyDTO {

    @JsonProperty(value="code")
    private String code;

    @JsonProperty(value="rate")
    private String rate;

    @JsonProperty(value="description")
    private String description;

    @JsonProperty(value="rate_float")
    private float rateFloat;

}
