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
            String sku = (String) prodotto.get("sku");
            if (sku != null && !sku.isEmpty()) {
                // Simula stock dal gestionale (mock)
                int stockDalGestionale = gestionaleClient.getStockBySku(sku);

                System.out.println("Prodotto: " + prodotto.get("name") +
                        ", SKU: " + sku +
                        ", Quantità attuale: " + prodotto.get("stock_quantity") +
                        ", Quantità gestionale: " + stockDalGestionale);
                wooCommerceClient.aggiornaStock((Integer) prodotto.get("id"), stockDalGestionale);
            }
        }
    }
}
