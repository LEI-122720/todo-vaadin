package com.example.cambio;


public class ExchangeResult {
    private String from;
    private String to;
    private double rate;
    private double convertedAmount;

    public ExchangeResult(String from, String to, double rate, double convertedAmount) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        this.convertedAmount = convertedAmount;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public double getRate() { return rate; }
    public double getConvertedAmount() { return convertedAmount; }
}
