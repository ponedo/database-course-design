package Viewer.SingleWordViewer;

import Viewer.MainFrame;
import MyComponents.MyButton;
import MyComponents.MyLabel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudyDialog extends JDialog {

    private MyButton btnLearn = new MyButton("Learn new words");
    private MyButton btnReview = new MyButton("Review");
    private MyButton btnCancel = new MyButton("Cancel");
    private MyLabel lbl = new MyLabel("Learn new words or review? (ง •_•)ง");
    private final JPanel contentPane = new JPanel();
    private MainFrame parent;

    class StudyDialogActionListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            if (ae.getActionCommand()=="Learn") {
                StudyFrame studyFrame = new StudyFrame(parent);
                parent.dispose();
                dispose();
            } else if (ae.getActionCommand()=="Review") {
                ReviewFrame reviewFrame = new ReviewFrame(parent);
                parent.dispose();
                dispose();
            } else {
                dispose();
            }
        }
    }

    public StudyDialog(MainFrame parent, boolean parentOperatble) {

        super(parent, parentOperatble);
        this.parent=parent;

        this.setTitle("Before we study...");
        setBounds(100, 100, 450, 400);
        setResizable(false);
        setLocationRelativeTo(parent);

        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new Color(194,162,227));
        setContentPane(contentPane);


        lbl.setBounds(50,20,400,30);
        lbl.setFont(new Font("", Font.BOLD, 20));
        contentPane.add(lbl);

        StudyDialogActionListener al = new StudyDialogActionListener();

        btnLearn.setBounds(100,90,250,50);
        btnLearn.setFont(new Font("", Font.PLAIN, 20));
        btnLearn.setActionCommand("Learn");
        btnLearn.addActionListener(al);
        contentPane.add(btnLearn);

        btnReview.setBounds(100,170,250,50);
        btnReview.setFont(new Font("", Font.PLAIN, 20));
        btnReview.setActionCommand("Review");
        btnReview.addActionListener(al);
        contentPane.add(btnReview);

        btnCancel.setBounds(100,250,250,50);
        btnCancel.setFont(new Font("", Font.PLAIN, 20));
        btnCancel.setActionCommand("Cancel");
        btnCancel.addActionListener(al);
        contentPane.add(btnCancel);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);

    }
}


