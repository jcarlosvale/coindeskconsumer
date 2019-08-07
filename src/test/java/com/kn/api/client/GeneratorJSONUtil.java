package com.kn.api.client;

public class GeneratorJSONUtil {
    public static String generateJSONFromRealData() {
        return "{\n" +
                "    \"time\": {\n" +
                "        \"updated\": \"Aug 6, 2019 20:31:00 UTC\",\n" +
                "        \"updatedISO\": \"2019-08-06T20:31:00+00:00\",\n" +
                "        \"updateduk\": \"Aug 6, 2019 at 21:31 BST\"\n" +
                "    },\n" +
                "    \"disclaimer\": \"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\n" +
                "    \"bpi\": {\n" +
                "        \"USD\": {\n" +
                "            \"code\": \"USD\",\n" +
                "            \"rate\": \"11,687.2950\",\n" +
                "            \"description\": \"United States Dollar\",\n" +
                "            \"rate_float\": 11687.295\n" +
                "        },\n" +
                "        \"BRL\": {\n" +
                "            \"code\": \"BRL\",\n" +
                "            \"rate\": \"46,232.4485\",\n" +
                "            \"description\": \"Brazilian Real\",\n" +
                "            \"rate_float\": 46232.4485\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }

    public static String generateJSONFromHistoricalData() {
        return "{\n" +
                "    \"bpi\": {\n" +
                "        \"2019-07-01\": 40710.9529,\n" +
                "        \"2019-07-02\": 41726.0932\n" +
                "    },\n" +
                "    \"disclaimer\": \"This data was produced from the CoinDesk Bitcoin Price Index. BPI value data returned as BRL.\",\n" +
                "    \"time\": {\n" +
                "        \"updated\": \"Jul 3, 2019 00:03:00 UTC\",\n" +
                "        \"updatedISO\": \"2019-07-03T00:03:00+00:00\"\n" +
                "    }\n" +
                "}";
    }
}
