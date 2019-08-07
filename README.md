# Simple CoinDesk API Consumer
by João Carlos (https://www.linkedin.com/in/joaocarlosvale/)

This project consists of a a command-line Java program that fetches data from the following public API: 
https://www.coindesk.com/api.
Once executed, the program requests the user to input a currency code (USD, EUR, GBP, etc.)
Once the user provides the currency code, the application displays the following information:
-	The current Bitcoin rate, in the requested currency
-	The lowest Bitcoin rate in the last 30 days, in the requested currency
-	The highest Bitcoin rate in the last 30 days, in the requested currency

## Technologies used:
* Java
* Spring
* Maven 

## Commands:

To generate JAR:

    mvn clean package

To run:

    java -jar target/CoinDeskConsumer-0.0.1.jar
    
To run tests:

    mvn test
