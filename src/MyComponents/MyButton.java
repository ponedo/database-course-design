package MyComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyButton extends JButton{

    public MyButton() {
        setup();
    }

    public MyButton(String text) {
        super(text);
        setup();
    }

    private void setup() {
        setBackground(new Color(149,118,254));
        setForeground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.white, 3));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(100,118,254));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(149,118,254));
            }
        });
    }
}
