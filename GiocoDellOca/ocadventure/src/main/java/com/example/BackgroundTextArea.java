package com.example;

import javax.swing.*;
import java.awt.*;

public class BackgroundTextArea extends JTextArea {
    private Image backgroundImage;

    public BackgroundTextArea(String imagePath) {
        try {
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, 5, 20, this);
        }
        super.paintComponent(g);
    }
}