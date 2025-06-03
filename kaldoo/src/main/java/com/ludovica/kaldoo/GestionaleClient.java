package com.ludovica.kaldoo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class GestionaleClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gestionale.api.url}")
    private String gestionaleUrl;

    @Value("${gestionale.api.token}")
    private String token;

    public int getStockBySku(String sku) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl(gestionaleUrl)
                    .queryParam("OP", "GIACENZA_ARTICOLO")
                    .queryParam("TOKEN", token)
                    .queryParam("COD_ARTICOLO", sku)
                    .toUriString();

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, null, Map.class);
            Map<String, Object> body = response.getBody();

            System.out.println("SKU richiesto: " + sku);
            System.out.println("Risposta JSON: " + body);

            Object recordsObj = body.get("records");

            if (recordsObj instanceof List) {
                List<Map<String, Object>> records = (List<Map<String, Object>>) recordsObj;
                if (!records.isEmpty() && records.get(0).containsKey("GIACENZA")) {
                    return Integer.parseInt(records.get(0).get("GIACENZA").toString());
                }
            } else if (recordsObj instanceof Map) {
                Map<String, Object> errorInfo = (Map<String, Object>) recordsObj;
                System.out.println("Nessuna giacenza trovata per SKU: " + sku + " - " + errorInfo.get("message"));
            }

            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }




}

