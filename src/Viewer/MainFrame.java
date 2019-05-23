package Viewer;

import Database.DBHandler;
import Viewer.AddWordDIalog.AddWordDialog;
import Viewer.AlterWordDialog.AlterWordDialog;
import MyComponents.MyButton;
import MyComponents.MyLabel;
import Viewer.SingleWordViewer.StudyDialog;
import Viewer.WordlistViewer.WordlistViewer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame implements ActionListener{

    private JPanel contentPane = new JPanel();
    private MyButton btnInsert = new MyButton("Add a word");
    private MyButton btnStudy = new MyButton("Study/Review");
    private MyButton btnViewLists = new MyButton("View lists");
    private MyButton btnAlter = new MyButton("Alter word records");
    private MyLabel lbl_welcome1 = null;
    private MyLabel lbl_welcome2 = null;
    private MyLabel lbl_toLearn = null;
    private MyLabel lbl_toReview = null;
    private MyLabel lbltoLearn = null;
    private MyLabel lbltoReview = null;

    private int toLearn;
    private int toReview;

    private DBHandler DBhandler= new DBHandler();

    private MainFrame self = this;

    public void updateNumbers() {
        toLearn = DBhandler.getToLearnNum();
        toReview = DBhandler.getToReviewNum();
        lbltoLearn.setText("" + toLearn);
        lbltoReview.setText("" + toReview);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource()==btnInsert) {
            AddWordDialog add = new AddWordDialog(this, false);
        } else if (ae.getSource()==btnStudy) {
            StudyDialog studyDialog = new StudyDialog(this, false);
        } else if (ae.getSource()==btnViewLists) {
            WordlistViewer wordlistViewer = new WordlistViewer(this);
        } else if (ae.getSource()==btnAlter) {
            AlterWordDialog alterWordDialog = new AlterWordDialog(this, false);
        }
    }

    public MainFrame() {

        toLearn = DBhandler.getToLearnNum();
        toReview = DBhandler.getToReviewNum();

        this.setTitle("Vocabulary check - 2016300030043 彭凯飞" );
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 750, 770);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(190,160,220));
        setContentPane(contentPane);
        setVisible(true);

        btnInsert.setBounds(200, 300, 300, 50);
        btnInsert.setFont(new Font("", Font.PLAIN, 20));
        btnInsert.addActionListener(this);
        contentPane.add(btnInsert);

        btnStudy.setBounds(200, 400, 300, 50);
        btnStudy.setFont(new Font("", Font.PLAIN, 20));
        btnStudy.addActionListener(this);
        contentPane.add(btnStudy);

        btnViewLists.setBounds(200, 500, 300, 50);
        btnViewLists.setFont(new Font("", Font.PLAIN, 20));
        btnViewLists.addActionListener(this);
        contentPane.add(btnViewLists);

        btnAlter.setBounds(200,600,300,50);
        btnAlter.setFont(new Font("", Font.PLAIN, 20));
        btnAlter.addActionListener(this);
        contentPane.add(btnAlter);

        lbl_welcome1 = new MyLabel();
        lbl_welcome1.setText("Hello! Welcome to my vocabulary program!");
        lbl_welcome1.setFont(new Font("", Font.PLAIN, 22));
        lbl_welcome1.setBounds(150,50, 500,30);
        contentPane.add(lbl_welcome1);

        lbl_welcome2 = new MyLabel();
        lbl_welcome2.setText("What would you like to do?");
        lbl_welcome2.setFont(new Font("", Font.PLAIN, 22));
        lbl_welcome2.setBounds(220,200, 500,30);
        contentPane.add(lbl_welcome2);

        lbl_toLearn = new MyLabel();
        lbl_toLearn.setText("Currently, you got          words to learn.");
        lbl_toLearn.setFont(new Font("", Font.PLAIN, 22));
        lbl_toLearn.setBounds(170,100, 500,30);
        contentPane.add(lbl_toLearn);

        lbl_toReview = new MyLabel();
        lbl_toReview.setText("and          words to review.(●'◡'●)");
        lbl_toReview.setFont(new Font("", Font.PLAIN, 22));
        lbl_toReview.setBounds(190,150, 500,30);
        contentPane.add(lbl_toReview);

        lbltoLearn = new MyLabel();
        lbltoLearn.setText("" + toLearn);
        lbltoLearn.setForeground(new Color(255,255,0));
        lbltoLearn.setFont(new Font("", Font.PLAIN, 22));
        lbltoLearn.setBounds(350,100, 500,30);
        contentPane.add(lbltoLearn);

        lbltoReview = new MyLabel();
        lbltoReview.setText("" + toReview);
        lbltoReview.setForeground(new Color(99,245,132));
        lbltoReview.setFont(new Font("", Font.PLAIN, 22));
        lbltoReview.setBounds(235,150, 500,30);
        contentPane.add(lbltoReview);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(self, "Are you sure to exit?")==0) {
                    DBhandler.closeAll();
                    System.exit(0);
                }
            }
        });
    }
}
