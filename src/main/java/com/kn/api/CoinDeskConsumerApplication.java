package com.kn.api;

import com.kn.api.client.CoinDeskClient;
import com.kn.api.domain.CurrentPrice;
import com.kn.api.domain.HistoricalPrice;
import com.kn.api.exception.CurrencyNotFoundException;
import com.kn.api.exception.DateException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Main class to interact with User
 */
public class CoinDeskConsumerApplication {

    public static void main(String args[]) {
        CoinDeskClient coinDeskClient = new CoinDeskClient();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Insert the currency ISO 4217 code (USD, EUR, GBP...): ");
        String currencyCode = scanner.nextLine().trim().toUpperCase();
        try {
            CurrentPrice currentPrice = coinDeskClient.getCurrentPriceByCurrencyCode(currencyCode);
            HistoricalPrice historicalPrice = coinDeskClient.getHistoricalPriceByCurrencyCode(currencyCode,
                    LocalDate.now().minusDays(30), LocalDate.now());
            print(currentPrice);
            print(historicalPrice);
        } catch (IOException e) {
            System.err.println("Unexpected error, contact the support and provide the message: " + e.getMessage());
        } catch (CurrencyNotFoundException | DateException e) {
            System.out.println(e.getMessage());
        }
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
