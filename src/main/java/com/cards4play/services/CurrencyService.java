package com.cards4play.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class CurrencyService {

    private final RestTemplate restTemplate;

    public CurrencyService() {
        this.restTemplate = new RestTemplate();
    }

    public double obtenerTasaCambioUsdToCop() {
        try {
            String url = "https://open.er-api.com/v6/latest/USD";
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey("rates")) {
                Map<String, Double> rates = (Map<String, Double>) response.get("rates");
                return rates.getOrDefault("COP", 3900.0);
            }
        } catch (Exception e) {
            System.err.println("Error al consumir la API externa. Usando tasa por defecto.");
        }
        return 3900.0;
    }
}