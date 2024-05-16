package com.example;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Giocatore {
    private String nome;
    private int posizione;
    private JLabel icon;

    public Giocatore(String nome, String imagePath) {
        this.nome = nome;
        this.posizione = 0;
        this.icon = new JLabel(new ImageIcon(imagePath));
    }

    public String getNome() {
        return nome;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public void muovi(int spostamento, Tabellone tabellone, InterfacciaUtente ui) {
        int nuovaPosizione = posizione + spostamento;
        if (nuovaPosizione >= 63) {
            nuovaPosizione = 63;  // rimane sulla casella finale
            ui.mostraVittoria(this);
        }
        tabellone.getCasella(nuovaPosizione).effetto(this);
        ui.aggiornaPosizioneGiocatore(this, nuovaPosizione);
    }

    public JLabel getIcon() {
        return icon;
    }

    public boolean haVinto() {
        return posizione >= 63; // Sostituisci CASCELLA_VITTORIA con il numero della casella vincente
    }
}
