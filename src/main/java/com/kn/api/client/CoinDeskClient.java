package com.kn.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kn.api.converter.Converter;
import com.kn.api.domain.CurrentPrice;
import com.kn.api.dto.CurrentPriceDTO;
import lombok.NonNull;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class CoinDeskClient {

    private static final String baseURL = "https://api.coindesk.com/v1/bpi/currentprice/%s.json";

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

}
