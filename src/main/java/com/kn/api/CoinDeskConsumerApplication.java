package com.kn.api;

import com.kn.api.client.CoinDeskClient;
import com.kn.api.domain.CurrentPrice;

import java.io.IOException;
import java.util.Scanner;

public class CoinDeskConsumerApplication {

    public static void main(String args[]) throws IOException {
        CoinDeskClient coinDeskClient = new CoinDeskClient();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Insert the currency ISO 4217 code (USD, EUR, GBP...): ");
        String currencyCode = scanner.nextLine().trim().toUpperCase();
        CurrentPrice currentPrice = coinDeskClient.getCurrentPriceByCurrencyCode(currencyCode);
        if (currentPrice != null) {
            System.out.printf("Current Bit Coin Rate [%s]: %f - %s\n", currentPrice.getCode(),
                    currentPrice.getValue(), currentPrice.getLastUpdate());
        } else {
            System.out.printf("Currency [%s] not found\n",currencyCode);
        }
    }


}
