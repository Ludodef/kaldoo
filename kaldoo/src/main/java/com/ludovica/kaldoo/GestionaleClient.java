package com.ludovica.kaldoo;

import org.springframework.stereotype.Service;

@Service
public class GestionaleClient {

    // Simulazione in attesa delle API vere
    public int getStockBySku(String sku) {
        // Simula una risposta dal gestionale (mock)
        return (int) (Math.random() * 50); // valore casuale tra 0 e 50
    }
}
