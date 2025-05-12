package com.ludovica.kaldoo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class WooCommerceClient {

    @Value("${woo.api.url}")
    private String wooApiUrl;

    @Value("${woo.api.consumerKey}")
    private String consumerKey;

    @Value("${woo.api.consumerSecret}")
    private String consumerSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, Object>> getProdotti() {
        String url = UriComponentsBuilder.fromHttpUrl(wooApiUrl + "/products")
                .queryParam("per_page", 10)
                .toUriString();

        HttpHeaders headers = buildAuthHeader();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        return response.getBody();
    }

    public void aggiornaStock(int productId, int newStock) {
        String url = wooApiUrl + "/products/" + productId;

        HttpHeaders headers = buildAuthHeader();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = "{\"stock_quantity\":" + newStock + "}";
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        restTemplate.exchange(url, HttpMethod.PUT, entity, Map.class);
    }

    private HttpHeaders buildAuthHeader() {
        String auth = consumerKey + ":" + consumerSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        return headers;
    }
}
