package com.kn.api.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class CurrentPrice {

    private final String code;

    private final Float value;

    private final String lastUpdate;
}

