package com.kn.api.converter;

import com.kn.api.domain.CurrentPrice;
import com.kn.api.dto.CurrencyDTO;
import com.kn.api.dto.CurrentPriceDTO;
import lombok.NonNull;

public class Converter {

    public CurrentPrice convert(@NonNull CurrentPriceDTO currentPriceDTO, @NonNull String currencyCode) {
        String lastUpdate = currentPriceDTO.getTimeObject().getUpdated();
        CurrencyDTO currencyDTO = currentPriceDTO.getCurrenciesDTO().getCurrencies().get(currencyCode);
        return (null == currencyCode) ?
                null :
                new CurrentPrice(currencyDTO.getCode(), currencyDTO.getRateFloat(), lastUpdate);
    }
}
