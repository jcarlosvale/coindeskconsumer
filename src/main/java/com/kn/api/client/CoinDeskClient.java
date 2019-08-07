package com.kn.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kn.api.converter.Converter;
import com.kn.api.domain.CurrentPrice;
import com.kn.api.domain.HistoricalPrice;
import com.kn.api.dto.CurrentPriceDTO;
import com.kn.api.dto.HistoricalCurrencyDTO;
import com.kn.api.exception.CurrencyNotFoundException;
import com.kn.api.exception.DateException;
import lombok.NonNull;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CoinDeskClient {

    private static final String baseURL = "https://api.coindesk.com/v1/bpi/currentprice/%s.json";

    private static final String historicalBaseURL = "https://api.coindesk.com/v1/bpi/historical/close.json?currency=%s&start=%s&end=%s";

    private static final String datePattern = "YYYY-MM-dd";

    protected RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Converter converter;


    public CoinDeskClient(){
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
        converter = new Converter();
    }

    /**
     *This method is used to get the current bitcoin price by a currency code
     * Steps:
     * 1. Call the URL and retrieve the JSON in a String format of a given currency code
     * 2. Convert the String in a Current Price DTO
     * 3. Convert the Current Price DTO in a Current Price domain object
     * @param currencyCode
     * @return the CurrentPrice given a currencyCode
     * @throws IOException
     */
    public CurrentPrice getCurrentPriceByCurrencyCode(@NonNull String currencyCode) throws IOException, CurrencyNotFoundException {
        try {
            currencyCode = currencyCode.toUpperCase();
            final String json = getCurrentPriceJson(currencyCode);
            final CurrentPriceDTO currentPriceDTO = toCurrentPriceDTO(json);
            final CurrentPrice currentPrice = converter.convert(currencyCode, currentPriceDTO);
            return currentPrice;
        } catch (HttpClientErrorException.NotFound exception) {
            throw new CurrencyNotFoundException(currencyCode);
        }
    }

    /**
     *This method is used to get the historical bitcoin price by a currency code, start and end date
     * Steps:
     * 1. Call the URL and retrieve the JSON in a String format of a given currency code and dates
     * 2. Convert the String in a Historical Price DTO
     * 3. Convert the Historical Price DTO in a Historical Price domain object
     * @param currencyCode
     * @param startDate
     * @param endDate
     * @return
     * @throws IOException
     */
    public HistoricalPrice getHistoricalPriceByCurrencyCode(@NonNull String currencyCode,
                                                            @NonNull LocalDate startDate,
                                                            @NonNull LocalDate endDate) throws IOException, DateException {
        currencyCode = currencyCode.toUpperCase();
        if (endDate.isBefore(startDate)) {
            throw new DateException("Invalid dates: the end date is before the start date.");
        }
        final String json = getHistoricalPriceJson(currencyCode, startDate, endDate);
        final HistoricalCurrencyDTO historicalCurrencyDTO = toHistoricalCurrencyDTO(json);
        final HistoricalPrice historicalPrice = converter.convert(currencyCode, historicalCurrencyDTO);
        return historicalPrice;
    }

    /**
     *
     * @param currencyCode
     * @return the JSON as String from GET BPI real-time data
     */
    protected String getCurrentPriceJson(String currencyCode) {
        String url = String.format(baseURL,currencyCode);
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * Transform the JSON string to CurrencyDTO
     * @param json
     * @return
     * @throws IOException
     */
    protected CurrentPriceDTO toCurrentPriceDTO(String json) throws IOException {
        return objectMapper.readValue(json, CurrentPriceDTO.class);
    }

    /**
     * Transform the JSON string to HistoricalCurrencyDTO
     * @param json
     * @return
     * @throws IOException
     */
    protected HistoricalCurrencyDTO toHistoricalCurrencyDTO(String json) throws IOException {
        return objectMapper.readValue(json, HistoricalCurrencyDTO.class);
    }

    /**
     *
     * @param currencyCode
     * @param startDate
     * @param endDate
     * @return the JSON in String format from GET BPI historical data
     */
    protected String getHistoricalPriceJson(@NonNull String currencyCode, @NonNull LocalDate startDate, @NonNull LocalDate endDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);

        String startDateStr = dateTimeFormatter.format(startDate);
        String endDateStr = dateTimeFormatter.format(endDate);

        String url = String.format(historicalBaseURL,currencyCode, startDateStr, endDateStr);
        return restTemplate.getForObject(url, String.class);
    }
}
