package Viewer.SingleWordViewer;

import javax.swing.*;
import java.awt.*;

public class WordPanel extends JPanel {

    private JLabel lblWord = new JLabel();
    private int width;
    private int height;

    public WordPanel(int x, int y, int width, int height){

        this.width = width;
        this.height = height;

        setBounds(x, y, width, height);
        setBackground(new Color(149,118,254));
        setBorder(BorderFactory.createLineBorder(Color.white,2));
        setLayout(null);

        lblWord.setForeground(new Color(255,255,10));
        lblWord.setFont(new Font("Serif",Font.BOLD,30));
        add(lblWord);
    }

    protected void setWord(String word) {
        lblWord.setText(word);
        int x_offset = (int)8.5*lblWord.getText().length();
        int lbl_width = 17*lblWord.getText().length() + 20;
        lblWord.setBounds(width/2-x_offset,height/2-15,lbl_width,35);
    }
}