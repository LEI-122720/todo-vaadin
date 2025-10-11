package com.example.cambio;


public class ExchangeResult {
    private String from;
    private String to;
    private double amount; // <-- New field for the original amount
    private double rate;
    private double convertedAmount;

    // Updated constructor to include amount
    public ExchangeResult(String from, String to, double amount, double rate, double convertedAmount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.rate = rate;
        this.convertedAmount = convertedAmount;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public double getAmount() { return amount; } // <-- New getter
    public double getRate() { return rate; }
    public double getConvertedAmount() { return convertedAmount; }
}