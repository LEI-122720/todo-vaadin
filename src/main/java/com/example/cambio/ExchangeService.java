package com.example.cambio;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeService {

    private final RestTemplate restTemplate = new RestTemplate();

    public ExchangeResult convert(String from, String to, double amount) {
        String url = String.format(
                "https://api.frankfurter.app/latest?amount=%s&from=%s&to=%s", amount, from, to
        );

        FrankfurterResponse response = restTemplate.getForObject(url, FrankfurterResponse.class);

        if (response == null || response.getRates() == null || !response.getRates().containsKey(to)) {
            throw new RuntimeException("Failed to retrieve exchange rate.");
        }

        double convertedAmount = response.getRates().get(to);
        double rate = convertedAmount / amount;
        System.out.println("Converted Amount: " + convertedAmount + " " + to);
        System.out.println("Rate used: " + rate);
        return new ExchangeResult(from, to, rate, convertedAmount);

    }
}

