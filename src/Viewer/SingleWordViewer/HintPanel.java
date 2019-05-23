package Viewer.SingleWordViewer;

import javax.swing.*;
import java.awt.*;

public class HintPanel extends JPanel {

    public JTextArea txtHint = new JTextArea();

    public HintPanel(int x, int y, int width, int height){

        setBounds(x, y, width, height);
        setBackground(new Color(149,118,254));
        setBorder(BorderFactory.createLineBorder(Color.white,2));
        setLayout(null);

        txtHint.setBounds(50,70,300,330);
        txtHint.setForeground(Color.white);
        txtHint.setFont(new Font("微软雅黑",Font.PLAIN,20));
        txtHint.setBackground(new Color(149,118,254));
        txtHint.setEditable(false);
        add(txtHint);
    }

}
