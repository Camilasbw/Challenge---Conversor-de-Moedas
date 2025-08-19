package com.conversor.service;

import com.conversor.model.ExchangeRateResponse;
import com.conversor.util.Logger;
import java.util.ArrayList;
import java.util.List;

public class CurrencyConverter {
    private final ExchangeRateService exchangeRateService;
    private final List<String> conversionHistory;
    private final Logger logger;

    public CurrencyConverter() {
        this.exchangeRateService = new ExchangeRateService();
        this.conversionHistory = new ArrayList<>();
        this.logger = new Logger();
    }

    public double convertCurrency(double amount, String fromCurrency, String toCurrency) throws Exception {
        ExchangeRateResponse response = exchangeRateService.getExchangeRates(fromCurrency);

        double fromRate = getRateFromResponse(response, fromCurrency);
        double toRate = getRateFromResponse(response, toCurrency);

        double convertedAmount = (amount / fromRate) * toRate;

        // Registrar a conversão
        String conversionRecord = String.format("%.2f %s = %.2f %s",
                amount, fromCurrency, convertedAmount, toCurrency);
        conversionHistory.add(conversionRecord);
        logger.logConversion(conversionRecord);

        return convertedAmount;
    }

    private double getRateFromResponse(ExchangeRateResponse response, String currency) {
        switch (currency) {
            case "USD":
                return response.getConversion_rates().getUSD();
            case "EUR":
                return response.getConversion_rates().getEUR();
            case "GBP":
                return response.getConversion_rates().getGBP();
            case "JPY":
                return response.getConversion_rates().getJPY();
            case "CAD":
                return response.getConversion_rates().getCAD();
            case "AUD":
                return response.getConversion_rates().getAUD();
            case "CHF":
                return response.getConversion_rates().getCHF();
            case "CNY":
                return response.getConversion_rates().getCNY();
            case "BRL":
                return response.getConversion_rates().getBRL();
            case "ARS":
                return response.getConversion_rates().getARS();
            default:
                throw new IllegalArgumentException("Moeda não suportada: " + currency);
        }
    }

    public List<String> getConversionHistory() {
        return new ArrayList<>(conversionHistory);
    }

    public void clearHistory() {
        conversionHistory.clear();
    }
}