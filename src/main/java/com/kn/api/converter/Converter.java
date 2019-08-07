package com.kn.api.converter;

import com.kn.api.domain.CurrentPrice;
import com.kn.api.domain.HistoricalPrice;
import com.kn.api.dto.CurrencyDTO;
import com.kn.api.dto.CurrentPriceDTO;
import com.kn.api.dto.HistoricalCurrencyDTO;
import lombok.NonNull;

/**
 * Converter used to convert DTO --> Domain Object
 */
public class Converter {

    public CurrentPrice convert(@NonNull String currencyCode, @NonNull CurrentPriceDTO currentPriceDTO) {
        String lastUpdate = currentPriceDTO.getTimeDTO().getUpdated();
        CurrencyDTO currencyDTO = currentPriceDTO.getCurrenciesDTO().getCurrencies().get(currencyCode);
        return (null == currencyCode) ?
                null :
                new CurrentPrice(currencyDTO.getCode(), currencyDTO.getRateFloat(), lastUpdate);
    }

    public HistoricalPrice convert(@NonNull String currencyCode, @NonNull HistoricalCurrencyDTO historicalCurrencyDTO) {

        HistoricalPrice historicalPrice = new HistoricalPrice(currencyCode);

        historicalCurrencyDTO.getCurrencySeriesDTO().getHistoricalDateMap().forEach((date, value) -> historicalPrice.add(date, value));

        return historicalPrice;
    }
}
