package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class InterfacciaUtente extends JFrame {
    private GiocoDellOca gioco;
    private Map<Integer, JPanel> casellePanels;
    private JTextArea logArea;
    private JLabel dadoLabel;

    public InterfacciaUtente(GiocoDellOca gioco) {
        this.gioco = gioco;
        setTitle("Gioco dell'Oca");
        setSize(925, 775);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/sfondo.png"));
        Image background = backgroundIcon.getImage();
    
        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, null);
            }
        };
    
        boardPanel.setLayout(null);
        boardPanel.setPreferredSize(new Dimension(900, 900));
        casellePanels = new HashMap<>();
    
        // Creazione e posizionamento delle caselle in forma spirale
        spiralPlacement(boardPanel);
        // Creazione e posizionamento delle caselle in forma spirale
        spiralPlacement(boardPanel);
    
        JScrollPane scrollPane = new JScrollPane(boardPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
        JButton rollButton = new JButton("Lancia il dado");
        rollButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gioco.giocaTurno();
            }
        });
    
        logArea = new JTextArea(5, 20);
        logArea.setEditable(false);
    
        dadoLabel = new JLabel("Dado: 0");
        
        add(scrollPane, BorderLayout.CENTER);
        add(rollButton, BorderLayout.SOUTH);
        add(new JScrollPane(logArea), BorderLayout.EAST);
        add(dadoLabel, BorderLayout.NORTH);
    }
    private void spiralPlacement(JPanel boardPanel) {
        int size = 60;
        int spacing = 30;
        int[][] spiral = new int[8][8];
        int x = 0, y = 0, dx = 1, dy = 0;
        int n = 8, m = 8, steps = n * m - 1;
    
        ImageIcon arrowRight = new ImageIcon(getClass().getResource("/frecciaDx.png"));
        ImageIcon arrowDown = new ImageIcon(getClass().getResource("/frecciaGiu.png"));
        ImageIcon arrowLeft = new ImageIcon(getClass().getResource("/frecciaSx.png"));
        ImageIcon arrowUp = new ImageIcon(getClass().getResource("/frecciaSu.png"));

        for (int i = 1; i <= steps; i++) {
            spiral[y][x] = i;
            int nx = x + dx, ny = y + dy;
            if (nx >= n || nx < 0 || ny >= m || ny < 0 || spiral[ny][nx] != 0) {
                int tmp = dx;
                dx = -dy;
                dy = tmp;
                nx = x + dx;
                ny = y + dy;
            }
            x = nx;
            y = ny;
        }
    
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (spiral[row][col] == 0) continue;
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createLineBorder(Color.black));
                panel.setBackground(Color.WHITE);
                panel.setSize(size, size);
                panel.setLayout(new FlowLayout(FlowLayout.CENTER));
                JLabel label = new JLabel(Integer.toString(spiral[row][col]));
                panel.add(label);
                boardPanel.add(panel);
                panel.setLocation(col * (size + spacing), row * (size + spacing));
                casellePanels.put(spiral[row][col] - 1, panel);
        
                boardPanel.setLayout(null);

                // Add an arrow between squares
                if (spiral[row][col] != 0 && spiral[row][col] < steps) {
                    JLabel arrowLabel = new JLabel();
                    int nextRow = row, nextCol = col;
                    if (col < n - 1 && spiral[row][col + 1] == spiral[row][col] + 1) nextCol++;
                    else if (row < m - 1 && spiral[row + 1][col] == spiral[row][col] + 1) nextRow++;
                    else if (col > 0 && spiral[row][col - 1] == spiral[row][col] + 1) nextCol--;
                    else if (row > 0 && spiral[row - 1][col] == spiral[row][col] + 1) nextRow--;
                    if (nextCol > col) { // Arrow pointing right
                        arrowLabel.setIcon(arrowRight);
                        arrowLabel.setBounds(col * (size + spacing) + size, row * (size + spacing) + size / 2, arrowLabel.getIcon().getIconWidth(), arrowLabel.getIcon().getIconHeight());
                    } else if (nextRow > row) { // Arrow pointing down
                        arrowLabel.setIcon(arrowDown);
                        arrowLabel.setBounds(col * (size + spacing) + size / 2, row * (size + spacing) + size, arrowLabel.getIcon().getIconWidth(), arrowLabel.getIcon().getIconHeight());
                    } else if (nextCol < col) { // Arrow pointing left
                        arrowLabel.setIcon(arrowLeft);
                        arrowLabel.setBounds(col * (size + spacing) - arrowLabel.getIcon().getIconWidth(), row * (size + spacing) + size / 2, arrowLabel.getIcon().getIconWidth(), arrowLabel.getIcon().getIconHeight());
                    } else if (nextRow < row) { // Arrow pointing up
                        arrowLabel.setIcon(arrowUp);
                        arrowLabel.setBounds(col * (size + spacing) + size / 2, row * (size + spacing) - arrowLabel.getIcon().getIconHeight(), arrowLabel.getIcon().getIconWidth(), arrowLabel.getIcon().getIconHeight());
                    }
                    boardPanel.add(arrowLabel);
                }
            }
        }
    }

    public void aggiornaPosizioneGiocatore(Giocatore giocatore, int nuovaPosizione) {
        // Remove the player's icon from its previous position
        if (giocatore.getPosizione() != -1) {
            JPanel previousPanel = casellePanels.get(giocatore.getPosizione());
            previousPanel.remove(giocatore.getIcon());
            previousPanel.revalidate();
            previousPanel.repaint();
        }
        
        // Update the player's position
        giocatore.setPosizione(nuovaPosizione);
    
        // Add the player's icon to its new position
        JPanel panel = casellePanels.get(giocatore.getPosizione());
        panel.add(giocatore.getIcon());
        
        logArea.append(giocatore.getNome() + " si muove alla casella " + (giocatore.getPosizione() + 1) + "\n");
    }

    public void mostraVittoria(Giocatore giocatore) {


        String audioFilePath = getClass().getResource("/vittoria.mp3").getPath();

        try {
            // Crea un oggetto File per il file audio
            File audioFile = new File(audioFilePath);

            // Ottieni un'istanza del file audio
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Ottieni un'istanza del clip audio
            Clip clip = AudioSystem.getClip();

            // Apri il clip audio con i dati del file audio
            clip.open(audioStream);

            // Riproduci il clip audio
            clip.start();

            // Aspetta fino a quando il clip finisce di suonare
            while (!clip.isRunning())
                Thread.sleep(10);
            while (clip.isRunning())
                Thread.sleep(10);

            // Chiudi il clip e il flusso audio
            clip.close();
            audioStream.close();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        
        // Create a new JFrame for the victory message
        JFrame frame = new JFrame("Vittoria!");
        dispose();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
    
        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        frame.add(panel);
    
       ImageIcon imageIcon = new ImageIcon(getClass().getResource("/Vittoria.png")); // Sostituisci con il percorso della tua immagine
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Allinea l'immagine al centro
        panel.add(imageLabel);

        // Aggiungi un messaggio di vittoria
        JLabel messageLabel = new JLabel(" Ha Vinto " + giocatore.getNome()); // Sostituisci con il metodo appropriato per ottenere il nome
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Allinea il messaggio al centro
        panel.add(messageLabel);

        // Aggiungi un bottone che ritorna al menu principale
        JButton button = new JButton("Torna al menu principale");
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Allinea il bottone al centro
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new instance of the main menu
                StartMenu mainMenu = new StartMenu();
                // Make it visible
                mainMenu.setVisible(true);
                // Dispose the current window
                frame.dispose();
            }
        });
        panel.add(button);
    
        // Show the frame
        frame.setVisible(true);
    }

    public void aggiornaDado(int risultato) {
        dadoLabel.setText("Dado: " + risultato);
    }

    public void rimuoviVecchieImmagini() {
        for (JPanel panel : casellePanels.values()) {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
        }
    }
}

