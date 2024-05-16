package com.example;


public class GiocoDellOca {
    private Giocatore[] giocatori;
    private Tabellone tabellone;
    private Dado dado;
    private InterfacciaUtente ui;

    public GiocoDellOca(String[] nomiGiocatori, String[] imagePaths) {
        tabellone = new Tabellone();
        dado = new Dado();
        giocatori = new Giocatore[nomiGiocatori.length];
        for (int i = 0; i < nomiGiocatori.length; i++) {
            giocatori[i] = new Giocatore(nomiGiocatori[i], imagePaths[i]);  // Assigns an image to each player
        }
    }

    public void setUI(InterfacciaUtente ui) {
        this.ui = ui;
    }

    public void giocaTurno() {
        for (Giocatore giocatore : giocatori) {
            int risultatoDado = dado.lancia();
            giocatore.muovi(risultatoDado, tabellone, ui);
            ui.aggiornaDado(risultatoDado);
            if (giocatore.haVinto()) {
                ui.mostraVittoria(giocatore); // Mostra un messaggio di vittoria per il giocatore
                break; // Interrompe il ciclo, terminando il gioco
            }
        }
    }
}


