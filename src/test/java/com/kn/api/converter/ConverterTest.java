package com.kn.api.converter;

import com.kn.api.domain.CurrentPrice;
import com.kn.api.domain.HistoricalPrice;
import com.kn.api.dto.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConverterTest {
    
    private Converter converter;
    
    @Before
    public void initialize() {
        this.converter = new Converter();
    }
    
    @Test
    public void testConvertCurrentPriceDTO() {
        String currencyCode = "BRL";
        TimeDTO timeDTO = new TimeDTO("Aug 6, 2019 20:31:00 UTC");
        CurrenciesDTO currenciesDTO = new CurrenciesDTO();
        currenciesDTO.addCurrencyInfo("USD", new CurrencyDTO("USD","11,687.2950","United States Dollar",11687.295f));
        currenciesDTO.addCurrencyInfo("BRL", new CurrencyDTO("BRL","46,232.4485","Brazilian Real",46232.4485f));
        CurrentPriceDTO currentPriceDTO = new CurrentPriceDTO(timeDTO, currenciesDTO);
        CurrentPrice expectedCurrentPrice = new CurrentPrice("BRL", 46232.4485f, "Aug 6, 2019 20:31:00 UTC");

        CurrentPrice actualCurrentPrice = converter.convert(currencyCode, currentPriceDTO);

        assertEquals(expectedCurrentPrice, actualCurrentPrice);
    }

    @Test
    public void testConvertHistoricalCurrencyDTO() {
        String currencyCode = "BRL";
        CurrencySeriesDTO currencySeriesDTO = new CurrencySeriesDTO();
        currencySeriesDTO.addHistoricalValue("2019-07-01", 40710.9529f);
        currencySeriesDTO.addHistoricalValue("2019-07-02", 41726.0932f);
        HistoricalCurrencyDTO historicalCurrencyDTO = new HistoricalCurrencyDTO(currencySeriesDTO);

        HistoricalPrice expectedHistoricalPrice = new HistoricalPrice(currencyCode);
        expectedHistoricalPrice.add("2019-07-01", 40710.9529f);
        expectedHistoricalPrice.add("2019-07-02", 41726.0932f);

        HistoricalPrice actualHistoricalPrice = converter.convert(currencyCode, historicalCurrencyDTO);

        assertEquals(expectedHistoricalPrice, actualHistoricalPrice);

    }

}