package com.kn.api.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Domain Class representing the Current Price in a Code, Value and Date of Update
 */
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class CurrentPrice {

    private final String code;

    private final Float value;

    private final String lastUpdate;
}

