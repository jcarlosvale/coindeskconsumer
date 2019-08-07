package com.kn.api.client;

import com.kn.api.domain.CurrentPrice;
import com.kn.api.domain.HistoricalPrice;
import com.kn.api.dto.*;
import com.kn.api.exception.CurrencyNotFoundException;
import com.kn.api.exception.DateException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CoinDeskClientTest {

    private CoinDeskClient coinDeskClient;

    @Before
    public void initialize() {
        coinDeskClient = new CoinDeskClient();
        coinDeskClient.restTemplate = Mockito.mock(RestTemplate.class);
    }

    @Test
    public void verifyCurrentBitCoinURL() {
        String currencyCode = "<CODE>";
        String expectedURL = "https://api.coindesk.com/v1/bpi/currentprice/<CODE>.json";
        String expectedResult = "any";

        when(coinDeskClient.restTemplate.getForObject(expectedURL, String.class)).thenReturn(expectedResult);

        String actualResult = coinDeskClient.getCurrentPriceJson(currencyCode);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void verifyHistoricalBitCoinURL() {
        String currencyCode = "<CODE>";
        LocalDate startDate = LocalDate.of(2019, 6, 1);
        LocalDate endDate = LocalDate.of(2019, 6, 30);
        String expectedURL = "https://api.coindesk.com/v1/bpi/historical/close.json?currency=<CODE>&start=2019-06-01&end=2019-06-30";
        String expectedResult = "any";

        when(coinDeskClient.restTemplate.getForObject(expectedURL, String.class)).thenReturn(expectedResult);

        String actualResult = coinDeskClient.getHistoricalPriceJson(currencyCode, startDate, endDate);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void verifyJSONtoCurrentPriceDTO() throws IOException{
        TimeDTO timeDTO = new TimeDTO("Aug 6, 2019 20:31:00 UTC");
        CurrenciesDTO currenciesDTO = new CurrenciesDTO();
        currenciesDTO.addCurrencyInfo("USD", new CurrencyDTO("USD","11,687.2950","United States Dollar",11687.295f));
        currenciesDTO.addCurrencyInfo("BRL", new CurrencyDTO("BRL","46,232.4485","Brazilian Real",46232.4485f));
        CurrentPriceDTO expectedCurrencyDTO = new CurrentPriceDTO(timeDTO, currenciesDTO);
        
        String json = GeneratorJSONUtil.generateJSONFromRealData();

        CurrentPriceDTO actualCurrencyDTO = coinDeskClient.toCurrentPriceDTO(json);

        assertEquals(expectedCurrencyDTO, actualCurrencyDTO);
    }

    @Test(expected = IOException.class)
    public void verifyInvalidJSONtoCurrentPriceDTO() throws IOException {
        String json = "invalid json";
        coinDeskClient.toCurrentPriceDTO(json);
    }

    @Test
    public void verifyJSONtoHistoricalCurrencyDTO() throws IOException {
        CurrencySeriesDTO currencySeriesDTO = new CurrencySeriesDTO();
        currencySeriesDTO.addHistoricalValue("2019-07-01", 40710.9529f);
        currencySeriesDTO.addHistoricalValue("2019-07-02", 41726.0932f);
        HistoricalCurrencyDTO expectedHistoricalCurrencyDTO = new HistoricalCurrencyDTO(currencySeriesDTO);
        String json = GeneratorJSONUtil.generateJSONFromHistoricalData();

        HistoricalCurrencyDTO actualHistoricalCurrencyDTO = coinDeskClient.toHistoricalCurrencyDTO(json);

        assertEquals(expectedHistoricalCurrencyDTO, actualHistoricalCurrencyDTO);
    }

    @Test(expected = IOException.class)
    public void verifyInvalidJSONtoHistoricalCurrencyDTO() throws IOException {
        String json = "invalid json";
        coinDeskClient.toHistoricalCurrencyDTO(json);
    }

    @Test
    public void testGetCurrentPriceByCurrencyCode() throws IOException, CurrencyNotFoundException {
        String currencyCode = "BRL";
        CurrentPrice expectedCurrentPrice = new CurrentPrice("BRL", 46232.4485f, "Aug 6, 2019 20:31:00 UTC");
        String json = GeneratorJSONUtil.generateJSONFromRealData();
        when(coinDeskClient.getCurrentPriceJson(currencyCode)).thenReturn(json);
        CurrentPrice actualCurrentPrice = coinDeskClient.getCurrentPriceByCurrencyCode(currencyCode);
        assertEquals(expectedCurrentPrice, actualCurrentPrice);
    }

    @Test(expected = CurrencyNotFoundException.class)
    public void testNotFoundCurrencyCode() throws IOException, CurrencyNotFoundException {
        String currencyCode = "XXX";
        when(coinDeskClient.getCurrentPriceJson(currencyCode))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND,null, null, null, null));
        coinDeskClient.getCurrentPriceByCurrencyCode(currencyCode);
    }

    @Test
    public void testgetHistoricalPriceByCurrencyCode() throws IOException, DateException {
        String currencyCode = "BRL";
        String json = GeneratorJSONUtil.generateJSONFromHistoricalData();
        LocalDate startDate = LocalDate.of(2019, 7, 1);
        LocalDate endDate = LocalDate.of(2019, 7, 2);
        HistoricalPrice expectedHistoricalPrice = new HistoricalPrice(currencyCode);
        expectedHistoricalPrice.add("2019-07-01", 40710.9529f);
        expectedHistoricalPrice.add("2019-07-02", 41726.0932f);
        when(coinDeskClient.getHistoricalPriceJson(currencyCode, startDate, endDate)).thenReturn(json);
        HistoricalPrice actualHistoricalPrice = coinDeskClient.getHistoricalPriceByCurrencyCode(currencyCode, startDate, endDate);
        assertEquals(expectedHistoricalPrice, actualHistoricalPrice);
        assertEquals(40710.9529f, actualHistoricalPrice.getLowestBitCoinRate().getKey(), 0);
        assertEquals(41726.0932f, actualHistoricalPrice.getHighestBitCoinRate().getKey(), 0);
        assertEquals("2019-07-01", actualHistoricalPrice.getLowestBitCoinRate().getValue());
        assertEquals("2019-07-02", actualHistoricalPrice.getHighestBitCoinRate().getValue());

    }

    @Test(expected = DateException.class)
    public void testDateException() throws IOException, DateException {
        String currencyCode = "XXX";
        String json = GeneratorJSONUtil.generateJSONFromHistoricalData();
        LocalDate startDate = LocalDate.of(2019, 7, 1);
        LocalDate endDate = LocalDate.of(2019, 7, 2);
        when(coinDeskClient.getHistoricalPriceJson(currencyCode, startDate, endDate)).thenReturn(json);
        coinDeskClient.getHistoricalPriceByCurrencyCode(currencyCode, endDate, startDate);
    }
}