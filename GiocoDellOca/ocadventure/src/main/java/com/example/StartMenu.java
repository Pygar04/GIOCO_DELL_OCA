package com.example;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class StartMenu extends JFrame {
    public StartMenu() {
        super("Schermata iniziale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);

        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/StartBackground.png"));
        Image background = backgroundIcon.getImage();

        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, null);
            }
        };
        boardPanel.setLayout(new BorderLayout());

        JButton btnPlayWithPerson = new JButton("Gioca con un'altra persona");
        JButton btnPlayWithBot = new JButton("Gioca con un bot");

        btnPlayWithPerson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame(false);
            }
        });

        btnPlayWithBot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame(true);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(btnPlayWithPerson);
        buttonPanel.add(btnPlayWithBot);

        boardPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(boardPanel);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void startGame(boolean withBot) {
        String playerName1 = JOptionPane.showInputDialog("Inserisci il nome del primo giocatore:");
        String playerName2 = withBot ? "Bot" : JOptionPane.showInputDialog("Inserisci il nome del secondo giocatore:");
    
        String[] playerNames = new String[]{playerName1, playerName2};
        String[] imagePaths = new String[]{
            getClass().getResource("/paperellaGialla.png").getPath(),
            getClass().getResource("/paperellaVerde.png").getPath()
        };
        GiocoDellOca gioco = new GiocoDellOca(playerNames, imagePaths);
        InterfacciaUtente ui = new InterfacciaUtente(gioco);
        gioco.setUI(ui);
        ui.setVisible(true);
        dispose(); // Chiude il menu iniziale
    }
}