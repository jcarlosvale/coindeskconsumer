package com.kn.api;

import com.kn.api.client.CoinDeskClient;
import com.kn.api.domain.CurrentPrice;
import com.kn.api.domain.HistoricalPrice;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class CoinDeskConsumerApplication {

    public static void main(String args[]) throws IOException {
        CoinDeskClient coinDeskClient = new CoinDeskClient();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Insert the currency ISO 4217 code (USD, EUR, GBP...): ");
        String currencyCode = scanner.nextLine().trim().toUpperCase();
        CurrentPrice currentPrice = coinDeskClient.getCurrentPriceByCurrencyCode(currencyCode);
        HistoricalPrice historicalPrice = coinDeskClient.getHistoricalPriceByCurrencyCode(currencyCode,
                LocalDate.now().minusDays(30), LocalDate.now());
        print(currentPrice);
        print(historicalPrice);
    }

    private static void print(HistoricalPrice historicalPrice) {
        if (historicalPrice != null) {
            System.out.printf("Highest Bit Coin Rate [%s]: %f - %s\n", historicalPrice.getCode(),
                    historicalPrice.getHighestBitCoinRate().getKey(), historicalPrice.getHighestBitCoinRate().getValue());
            System.out.printf("Lowest Bit Coin Rate [%s]: %f - %s\n", historicalPrice.getCode(),
                    historicalPrice.getLowestBitCoinRate().getKey(), historicalPrice.getLowestBitCoinRate().getValue());
        } else {
            System.out.printf("Historical Currency [%s] not found\n",historicalPrice.getCode());
        }
    }

    private static void print(CurrentPrice currentPrice) {
        if (currentPrice != null) {
            System.out.printf("Current Bit Coin Rate [%s]: %f - %s\n", currentPrice.getCode(),
                    currentPrice.getValue(), currentPrice.getLastUpdate());
        } else {
            System.out.printf("Currency [%s] not found\n",currentPrice.getCode());
        }
    }


}
