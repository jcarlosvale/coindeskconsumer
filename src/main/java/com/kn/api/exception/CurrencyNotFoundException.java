package com.kn.api.exception;

import lombok.NonNull;

public class CurrencyNotFoundException extends Exception {
    public CurrencyNotFoundException(@NonNull String currencyCode) {
        super(String.format("Currency code [%s] not found. Try again.", currencyCode));
    }
}
