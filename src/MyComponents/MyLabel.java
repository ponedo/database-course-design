package MyComponents;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel {

    public MyLabel() {
        setup();
    }

    public MyLabel(String text) {
        super(text);
        setup();
    }

    private void setup() {
        setForeground(new Color(90,10,0));
    }

}
