package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class GUI {
    //a being the Json parser
    private final JSONObjectProcessing a;

    public GUI() throws Exception {
        a = new JSONObjectProcessing();

        //initial jFrame
        SwingUtilities.invokeLater(()->{
            JFrame frame = new JFrame("Cat Information");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            generate(frame);

            frame.setVisible(true);
        });
    }

    //generates the image of the frame
    public JLabel generateImage(JFrame frame){
        frame.getContentPane().removeAll();

        frame.setSize(850, 850);

        //image scaling
        try {
            URL url = new URL(a.imageURL());
            BufferedImage originalImage = ImageIO.read(url);
            int maxWidth = 800;
            int maxHeight = 800;

            int width = originalImage.getWidth();
            int height = originalImage.getHeight();

            double wRatio = (double) maxWidth / width;
            double hRatio = (double) maxHeight / height;
            double scale = Math.min(wRatio, hRatio);

            int newWidth = (int) (width * scale);
            int newHeight = (int) (height * scale);

            Image result =  originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(result));
        } catch (Exception e) {
            e.printStackTrace();
            frame.add(new JLabel("Failed to load image"));
        }
        return new JLabel();
    }

    //the actual frame generation
    public void generate(JFrame frame){
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
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

        //when pressed call this
        button.addActionListener(e -> {
            try {
                a.newCat();
                SwingUtilities.invokeLater(() -> {
                    generate(frame);
                    frame.setVisible(true);
                });
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        //creates a dialog with all the cats in catsDiscovered as a list of buttons
        buttonList.addActionListener(e -> {
            JDialog dialog = new JDialog(frame, "Cats Discovered");
//          dialog.add(new JList<>(a.getList().toArray()));
            dialog.setLayout(new GridLayout(0,1));
            dialog.setResizable(false);

            for(int i = 0; i <a.getList().size(); i++){
                int finalI = i;
                //action is the action performed when clicking on cat's name
                //it creates another dialog with the information inside.
                Action action = new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JDialog info = new JDialog(dialog, "Information");
                        info.setSize(500, 250);
                        info.setLayout(new FlowLayout());
                        info.setBackground(Color.white);

                        JTextArea text = new JTextArea();
                        text.setEditable(false);
                        text.setText("More information on " + a.getList().get(finalI).get(0) + " in the websites below");
                        
                        JTextArea percent = new JTextArea();
                        percent.setEditable(false);
                        int numOfAppe = Integer.parseInt(a.getList().get(finalI).get(2));
                        percent.setText("Number of times seen(not including specific generation): " + numOfAppe +"\nPercent of all cats seen: " + numOfAppe +"/" +a.totalCats() + "   " + ((double)numOfAppe/a.totalCats()));
                        
                        JPanel panel = new JPanel();
                        panel.setPreferredSize(new Dimension(500, 120));
                        panel.setBackground(Color.white);

                        panel.add(text);


                        ArrayList<String> list;
                        try {
                            list = a.moreInfo(a.getList().get(finalI).get(1));
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                        for (String s : list) {
                            SwingLink a = new SwingLink(s, s);
                            panel.add(a);
                        }

                        info.add(panel);
                        info.add(percent);
                        JButton generateSpecificCat = new JButton("Generate " + a.getList().get(finalI).get(0));
                        info.add(generateSpecificCat);

                        //generates a specific cat when pressed
                        generateSpecificCat.addActionListener(e1 -> {
                            try {
                                a.newCatId(a.getList().get(finalI).get(1));
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            SwingUtilities.invokeLater(() ->{
                                generate(frame);
                                frame.setVisible(true);
                            });
                        });
                        info.setVisible(true);
                    }

                };
                JButton catButton = new JButton(action);
                catButton.setText(a.getList().get(i).get(0));
                dialog.add(catButton);

            }

            dialog.setSize(100,20 * a.getList().size() + 40);
            dialog.setVisible(true);
        });
    }

}
