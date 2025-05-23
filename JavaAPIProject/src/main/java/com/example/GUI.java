package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class GUI {
    private final JSONObjectProcessing a;

    public GUI() throws Exception {
        a = new JSONObjectProcessing();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cat Information");
            frame.setLocation(0,0);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            generate(frame);

            frame.setVisible(true);
        });
    }

    public JLabel generateImage(JFrame frame){
        frame.getContentPane().removeAll();

        int width = a.imageWidth();
        int height = a.imageHeight();
        frame.setSize(850, 850);


        try {
            // Replace this with your image URL
            URL url = new URL(a.imageURL());
            BufferedImage originalImage = ImageIO.read(url);
            int maxWidth = 800;
            int maxHeight = 800;

            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            double widthRatio = (double) maxWidth / originalWidth;
            double heightRatio = (double) maxHeight / originalHeight;
            double scale = Math.min(widthRatio, heightRatio); // Keep aspect ratio

            int newWidth = (int) (originalWidth * scale);
            int newHeight = (int) (originalHeight * scale);

            Image scaledImage =  originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
            frame.add(new JLabel("Failed to load image"));
        }
        return new JLabel();
    }


    public void generate(JFrame frame){

        JPanel panel = new JPanel();
        JTextArea info = new JTextArea(3,50);
        JButton button = new JButton("New Cat");
        JButton buttonList = new JButton("Discovered Cats");


        info.setEditable(false);
        info.setText(a.getInfo());
        info.setLineWrap(true);
        info.setWrapStyleWord(true);

        panel.add(info);
        panel.add(button);
        panel.add(buttonList);
        panel.add(generateImage(frame));
        frame.add(panel);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    a.newCat();
                    SwingUtilities.invokeLater(()->{
                        generate(frame);
                    });
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(a.getList());

            }
        });
    }
}
