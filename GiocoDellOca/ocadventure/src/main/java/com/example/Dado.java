package com.example;

public class Dado {
    double dado1, dado2;
    public int lancia() {

        dado1=(Math.random() * 6) + 1;
        dado2=(Math.random() * 6) + 1;
        return((int) (dado1+dado2));
    }
}
