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

public class StudyFrame extends JFrame implements ActionListener{

    private MyLabel lblWelcome = new MyLabel("Ready to Learn?   o(*￣▽￣*)ブ");
    private MyButton btnStart = new MyButton("Start learning!");

    private MyButton btnNext = new MyButton("Next");
    private MyButton btnLast = new MyButton("Last");
    private MyButton btnHint = new MyButton("Hint");
    private MyButton btnEnd = new MyButton("End learning");
    private WordPanel display = null;
    private HintPanel hint = null;
    private JPanel contentPane = new JPanel();
    private MainFrame parent;
    private DBHandler DBhandler = new DBHandler();

    private boolean hinted = false;
    private WordEntry current_entry;

    public void setHinted(boolean hinted) {
        this.hinted = hinted;
    }

    public StudyFrame(MainFrame parent) {

        this.parent = parent;

        this.setTitle("Learning");
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

        display = new WordPanel(50,70,500,150);
        contentPane.add(display);

        hint = new HintPanel(50,170,500,420);
        contentPane.add(hint);

        btnNext.setBounds(390,600,160,50);
        btnNext.setFont(new Font("", Font.PLAIN, 16));
        btnNext.addActionListener(this);
        contentPane.add(btnNext);

        btnLast.setBounds(50,600,160,50);
        btnLast.setFont(new Font("", Font.PLAIN, 16));
        btnLast.addActionListener(this);
        contentPane.add(btnLast);

        btnHint.setBounds(220,600,160,50);
        btnHint.setFont(new Font("", Font.PLAIN, 16));
        btnHint.addActionListener(this);
        contentPane.add(btnHint);

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
            DBhandler.closeAll();
            parent.updateNumbers();
            parent.setVisible(true);
            dispose();
        }
    }

    private void printWord() {
        display.setWord(current_entry.getWord());
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
            DBhandler.prepareToLearnSet();
            current_entry = DBhandler.getNextWordEntry();
            printWord();
        } else if (ae.getSource()==btnNext) {
            DBhandler.setLearned(current_entry.getWord());
            clearHint();
            current_entry = DBhandler.getNextWordEntry();
            printWord();
        } else if (ae.getSource()==btnLast) {
            clearHint();
            current_entry = DBhandler.getPreviousWordEntry();
            printWord();
        } else if (ae.getSource()==btnHint) {
            printHint();
        } else if (ae.getSource()==btnEnd) {
            backtoMainFrame();
        }
    }
}
