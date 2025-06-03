package com.ludovica.kaldoo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SyncService {

    private final WooCommerceClient wooCommerceClient;
    private final GestionaleClient gestionaleClient;

    public SyncService(WooCommerceClient wooCommerceClient, GestionaleClient gestionaleClient) {
        this.wooCommerceClient = wooCommerceClient;
        this.gestionaleClient = gestionaleClient;
    }

    public void sincronizzaProdotti() {
        List<Map<String, Object>> prodotti = wooCommerceClient.getProdotti();
        for (Map<String, Object> prodotto : prodotti) {
            String nome = (String) prodotto.get("name");
            String sku = (String) prodotto.get("sku");
            Integer id = (Integer) prodotto.get("id");
            Integer stockWoo = (Integer) prodotto.get("stock_quantity");

            if (sku != null && !sku.isEmpty()) {
                int stockDalGestionale = gestionaleClient.getStockBySku(sku);

                System.out.printf("Prodotto: %s | SKU: %s | Woo: %d | Gestionale: %d%n",
                        nome, sku, stockWoo, stockDalGestionale);

                wooCommerceClient.aggiornaStock(id, stockDalGestionale);
            }
        }
    }
}
