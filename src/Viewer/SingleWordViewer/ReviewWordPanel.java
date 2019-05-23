package Viewer.SingleWordViewer;

import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;

public class ReviewWordPanel extends JPanel {

    private JLabel lblWord = new JLabel();
    private JLabel lblImportance = new JLabel();
    private int width;
    private int height;

    public ReviewWordPanel(int x, int y, int width, int height) {

        this.width = width;
        this.height = height;

        setBounds(x, y, width, height);
        setBackground(new Color(149,118,254));
        setBorder(BorderFactory.createLineBorder(Color.white,2));
        setLayout(null);

        lblWord.setForeground(new Color(255,255,10));
        lblWord.setFont(new Font("Serif", Font.BOLD, 30));
        add(lblWord);

        lblImportance.setForeground(new Color(255,190,10));
        lblImportance.setFont(new Font("Serif", Font.BOLD, 22));
        add(lblImportance);
    }

    protected void setWord(String word) {
        lblWord.setText(word);
        int x_offset = (int) 8.5*lblWord.getText().length();
        int lbl_width = 17*lblWord.getText().length() + 20;
        lblWord.setBounds(width/2-x_offset,height/2-15,lbl_width,35);
    }

    protected void setImportance(int importance) {
        switch (importance) {
            case 1: lblImportance.setText("★"); break;
            case 2: lblImportance.setText("★★"); break;
            case 3: lblImportance.setText("★★★"); break;
            case 4: lblImportance.setText("★★★★"); break;
            case 5: lblImportance.setText("★★★★★"); break;
        }
        int importance_offset = 13 * lblImportance.getText().length();
        int lblImportance_width = 28 * lblImportance.getText().length() + 20;
        lblImportance.setBounds(width / 2 - importance_offset, height / 2 - 60, lblImportance_width, 30);
    }
}