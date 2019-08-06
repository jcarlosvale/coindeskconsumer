package com.kn.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kn.api.converter.Converter;
import com.kn.api.domain.CurrentPrice;
import com.kn.api.domain.HistoricalPrice;
import com.kn.api.dto.CurrentPriceDTO;
import com.kn.api.dto.HistoricalCurrencyDTO;
import lombok.NonNull;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CoinDeskClient {

    private static final String baseURL = "https://api.coindesk.com/v1/bpi/currentprice/%s.json";

    private static final String historicalBaseURL = "https://api.coindesk.com/v1/bpi/historical/close.json?currency=%s&start=%s&end=%s";

    private static final String datePattern = "YYYY-MM-dd";

    public CurrentPrice getCurrentPriceByCurrencyCode(@NonNull String currencyCode) throws IOException {
        String json = getCurrentPriceJson(currencyCode);
        CurrentPriceDTO currentPriceDTO = toCurrencyDTO(json);
        Converter converter = new Converter();
        CurrentPrice currentPrice = converter.convert(currentPriceDTO, currencyCode);
        return currentPrice;
    }

    private String getCurrentPriceJson(String currencyCode) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(baseURL,currencyCode);
        return restTemplate.getForObject(url, String.class);
    }

    private CurrentPriceDTO toCurrencyDTO(String json) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(json, CurrentPriceDTO.class);
    }

    public HistoricalPrice getHistoricalPriceByCurrencyCode(@NonNull String currencyCode,
                                                            @NonNull LocalDate startDate,
                                                            @NonNull LocalDate endDate) throws IOException {
        if (endDate.isBefore(startDate)) {
            throw new DateTimeException("Invalid dates: the end date is before the start date.");
        }

        String json = getHistoricalPriceJson(currencyCode, startDate, endDate);
        HistoricalCurrencyDTO historicalCurrencyDTO = toHistoricalCurrencyDTO(json);
        Converter converter = new Converter();
        HistoricalPrice historicalPrice = converter.convert(currencyCode, historicalCurrencyDTO);
        return historicalPrice;
    }

    private HistoricalCurrencyDTO toHistoricalCurrencyDTO(String json) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(json, HistoricalCurrencyDTO.class);
    }

    private String getHistoricalPriceJson(String currencyCode, LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);

        String startDateStr = dateTimeFormatter.format(startDate);
        String endDateStr = dateTimeFormatter.format(endDate);

        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(historicalBaseURL,currencyCode, startDateStr, endDateStr);
        return restTemplate.getForObject(url, String.class);
    }
}
