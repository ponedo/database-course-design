package Viewer.SingleWordViewer;

import Database.DBHandler;
import Entity.WordEntry;
import Viewer.MainFrame;
import MyComponents.MyButton;
import MyComponents.MyLabel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ReviewFrame extends JFrame implements ActionListener{

    private MyLabel lblWelcome = new MyLabel("Ready to review?   o(*￣▽￣*)ブ");
    private MyButton btnStart = new MyButton("Start reviewing!");

    private MyButton btnNext = new MyButton("Next");
    private MyButton btnLast = new MyButton("Last");
    private MyButton btnKnow = new MyButton("Know");
    private MyButton btnDontKnow = new MyButton("Don't know");
    private MyButton btnEnd = new MyButton("End reviewing");
    private ReviewWordPanel display = null;
    private HintPanel hint = null;
    private JPanel know_next_pane = new JPanel();
    private JPanel contentPane = new JPanel();
    private MainFrame parent;
    private DBHandler DBhandler = new DBHandler();

    private boolean hinted = false;
    private WordEntry current_entry;

    private static final int KNOW_TO_NEXT = 0;
    private static final int NEXT_TO_KNOW = 1;

    public void setHinted(boolean hinted) {
        this.hinted = hinted;
    }

    public ReviewFrame(MainFrame parent) {

        this.parent = parent;

        this.setTitle("Reviewing");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 600, 700);
        setResizable(false);
        setLocationRelativeTo(parent);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(194,162,227));
        setContentPane(contentPane);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                backtoMainFrame();
            }
        });

        lblWelcome.setBounds(150,300,380,30);
        lblWelcome.setFont(new Font("", Font.BOLD, 20));
        contentPane.add(lblWelcome);

        btnStart.setBounds(200,350,200,100);
        btnStart.setFont(new Font("", Font.BOLD, 20));
        btnStart.addActionListener(this);
        contentPane.add(btnStart);
    }

    private void start() {

        contentPane.removeAll();

        display = new ReviewWordPanel(50,70,500,150);
        contentPane.add(display);

        hint = new HintPanel(50,170,500,420);
        contentPane.add(hint);

        know_next_pane.setBounds(305,600,245,50);
        know_next_pane.setLayout(null);
        contentPane.add(know_next_pane);

        btnNext.setBounds(0,0,245,50);
        btnNext.setFont(new Font("", Font.PLAIN, 16));
        btnNext.addActionListener(this);

        btnKnow.setBounds(0,0,245,50);
        btnKnow.setFont(new Font("", Font.PLAIN, 16));
        btnKnow.addActionListener(this);
        know_next_pane.add(btnKnow);

        btnDontKnow.setBounds(50,600,245,50);
        btnDontKnow.setFont(new Font("", Font.PLAIN, 16));
        btnDontKnow.addActionListener(this);
        contentPane.add(btnDontKnow);

        btnEnd.setBounds(50,10,150,50);
        btnEnd.setFont(new Font("", Font.PLAIN, 16));
        btnEnd.addActionListener(this);
        contentPane.add(btnEnd);

        repaint();
    }

    private void backtoMainFrame() {
        int exit = JOptionPane.showConfirmDialog
                (this, "Are you sure to stop studying?", "Exit confirm", JOptionPane.YES_NO_OPTION);
        if (exit==0) {
            DBhandler.deleteToReviewWords();
            DBhandler.closeAll();
            parent.updateNumbers();
            parent.setVisible(true);
            dispose();
        }
    }

    private void buttonShift(int operation) {
        know_next_pane.removeAll();
        if (operation==NEXT_TO_KNOW) {
            know_next_pane.add(btnKnow);
        } else if (operation==KNOW_TO_NEXT){
            know_next_pane.add(btnNext);
        }
        know_next_pane.repaint();
    }

    private void printWord() {
        display.setWord(current_entry.getWord());
        display.setImportance(current_entry.getImportance());
    }

    private void clearHint() {
        hint.txtHint.setText("");
        setHinted(false);
    }

    private void printHint() {

        if (!hinted) {
            setHinted(true);
            ArrayList<String>[] meaning_sets = current_entry.pos;
            int length = meaning_sets.length;
            int pos_index = 1;
            String[] pos = {
                    "n.",
                    "v.",
                    "adj.",
                    "adv.",
                    "pron.",
                    "num.",
                    "art.",
                    "prep.",
                    "conj.",
                    "interj.",
            };

            for (int i = 0; i < length; i++) {
                if (meaning_sets[i].size() != 0) {
                    hint.txtHint.append(pos_index + ". " + pos[i] + ":\n");
                    int meaning_number = meaning_sets[i].size();
                    int meaning_index = 1;
                    for (int j = 0; j < meaning_number; j++) {
                        hint.txtHint.append("    " + meaning_index + ". " + meaning_sets[i].get(j) + ";\n");
                        meaning_index++;
                    }
                    hint.txtHint.append("\n");
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource()==btnStart) {
            start();
            DBhandler.prepareToReviewSet();
            current_entry = DBhandler.getNextWordEntry();
            printWord();
        } else if (ae.getSource()==btnNext) {
            clearHint();
            current_entry = DBhandler.getNextWordEntry();
            printWord();
            buttonShift(NEXT_TO_KNOW);
        } else if (ae.getSource()==btnKnow) {
            DBhandler.updateToReviewInfo(current_entry.getWord(), current_entry.getImportance(), DBHandler.IMPORTANCE_DECREASE);
            clearHint();
            current_entry = DBhandler.getNextWordEntry();
            printWord();
        } else if (ae.getSource()== btnDontKnow) {
            DBhandler.updateToReviewInfo(current_entry.getWord(), current_entry.getImportance(), DBHandler.IMPORTANCE_INCREASE);
            printHint();
            buttonShift(KNOW_TO_NEXT);
        } else if (ae.getSource()==btnEnd) {
            backtoMainFrame();
        }
    }
}
