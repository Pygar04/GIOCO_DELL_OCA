package com.example;

public class Casella {
    private int numero;

    public Casella(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public void effetto(Giocatore giocatore) {
        // Effetti specifici come avanzare o retrocedere possono essere implementati qui
    }
}
