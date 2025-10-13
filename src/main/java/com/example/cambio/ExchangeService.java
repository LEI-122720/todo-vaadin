package com.example.cambio;

import org.javamoney.moneta.Money;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ExchangeService {

    public ExchangeResult convert(String from, String to, double amount) {
        // Define currencies
        CurrencyUnit fromCurrency = Monetary.getCurrency(from);
        CurrencyUnit toCurrency = Monetary.getCurrency(to);

        // Create a monetary amount
        MonetaryAmount monetaryAmount = Money.of(amount, fromCurrency);

        // Use the default exchange rate provider (e.g., ECB)
        ExchangeRateProvider provider = MonetaryConversions.getExchangeRateProvider("ECB");

        // Get the conversion and apply it
        CurrencyConversion conversion = provider.getCurrencyConversion(toCurrency);
        MonetaryAmount convertedAmount = monetaryAmount.with(conversion);

        // Round to 2 decimal places (standard for currency)
        BigDecimal roundedValue = convertedAmount.getNumber()
                .numberValue(BigDecimal.class)
                .setScale(2, RoundingMode.HALF_UP);
        double convertedValue = roundedValue.doubleValue();

        // Extract rate info
        double rate = provider.getExchangeRate(fromCurrency, toCurrency).getFactor().doubleValue();


        System.out.println("Converted Amount: " + convertedValue + " " + to);
        System.out.println("Rate used: " + rate);

        // Pass the original 'amount' to the updated constructor
        return new ExchangeResult(from, to, amount, rate, convertedValue);
    }
}