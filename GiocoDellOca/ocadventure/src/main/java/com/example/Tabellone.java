package com.example;


public class Tabellone {
    private Casella[] caselle;

    public Tabellone() {
        caselle = new Casella[63];  //63 caselle
        for (int i = 0; i < caselle.length; i++) {
            caselle[i] = new Casella(i);
        }
    }

    public Casella getCasella(int numero) {
        return caselle[numero];
    }

    public int getNumeroCaselle() {
        return caselle.length;
    }
}

