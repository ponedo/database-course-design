package Viewer.WordlistViewer;

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
import java.util.ArrayList;

public class WordlistViewer extends JFrame implements ActionListener{

    private MyLabel lblSelect = new MyLabel("Select a list to view (*^_^*):");
    private MyLabel lblColumns = new MyLabel();
    private DefaultListModel<String> model = new DefaultListModel<String>();
    private JList<String> displayList = new JList<String>();
    private JScrollPane displayPane = new JScrollPane(displayList);
    private JComboBox listSelect = new JComboBox<String>(new String[]
            {"To review list", "Remembered list", "Integrated list"});
    private MyButton btnView = new MyButton("View");

    private DBHandler DBhandler = new DBHandler();

    private void prepareList() {
        switch (listSelect.getSelectedIndex()) {
            case 0: DBhandler.prepareToReviewSet(); break;
            case 1: DBhandler.prepareRememberedSet(); break;
            case 2: DBhandler.prepareWholeSet(); break;
            default: break;
        }
    }
    /**This method is actually a three layer loop
     * for (words){
     *     for (all pos) {
     *         for (meanings) {
     *             print(line);
     *         }
     *     }
     * }*/
    private void showList() {
        //temps
        String line;
        WordEntry entry;
        String word;
        String pos;
        String meaning;
        boolean first_pos;
        boolean first_meaning;
        //Clear the display area first
        model.removeAllElements();
        //Get the words and info to setup the data of the list
        while (true) {
            //Get the entry of the word
            entry = DBhandler.getNextWordEntry();
            //if end, break
            if (entry.getWord().equals("No next word!")) {
                break;
            }
            //Get the word
            word = entry.getWord();
            //Prepare temps for info query
            ArrayList<String>[] meaning_sets = entry.pos;
            int length = meaning_sets.length;
            String[] pos_set = {
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
            //outer loop for pos, inner loop for meaning
            first_pos = true;
            for (int i=0; i<length; i++) {
                //Scan non-empty meaning sets
                if (meaning_sets[i].size()!=0) {
                    pos = pos_set[i];
                    int meaning_number = meaning_sets[i].size();
                    //Scan meanings for a specific pos
                    first_meaning = true;
                    for (int j=0; j<meaning_number; j++) {

                        meaning = meaning_sets[i].get(j);

                        if (first_pos) {
                            //Alignment
                            String alignment1="";
                            int alignment1_num = 20-word.length();
                            for (int k=0; k<alignment1_num; k++) {
                                alignment1 = alignment1 + " ";
                            }
                            String alignment2="";
                            int alinment2_num = 8-pos.length();
                            for (int k=0; k<alinment2_num; k++) {
                                alignment2 = alignment2 + " ";
                            }
                            //Compose the line for printing
                            line = word + alignment1 + pos + alignment2 + meaning;
                            first_pos = false;
                            first_meaning = false;
                        } else {
                            if (first_meaning) {
                                String alignment2="";
                                int alignment2_num = 8-pos.length();
                                for (int k=0; k<alignment2_num; k++) {
                                    alignment2 = alignment2 + " ";
                                }
                                //Compose the line for printing
                                line = "                    " +
                                        pos + alignment2 + meaning;
                                first_meaning = false;
                            } else {
                                line = "                            "
                                        + meaning;
                            }
                        }

                        model.addElement(line);

                    }
                }
            }
        }
        displayList.setModel(model);
    }

    public WordlistViewer(MainFrame parent) {

        JPanel contentPane = new JPanel();
        setBounds(100,100,800,630);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(70,70,70));
        contentPane.setBorder(new EmptyBorder(5,5,5,5));

        displayList.setFont(new Font("DialogInput", Font.BOLD, 18));
        displayList.setBackground(new Color(55,55,55));
        displayList.setForeground(new Color(255,255,255));
        displayPane.setBounds(40,130, 720,370);
        displayPane.setBackground(new Color(194,162,227));
        contentPane.add(displayPane);

        listSelect.setBounds(385,40,180,30);
        listSelect.setFont(new Font("", Font.PLAIN, 18));
        contentPane.add(listSelect);

        lblSelect.setFont(new Font("", Font.BOLD, 20));
        lblSelect.setForeground(new Color(250,250,230));
        lblSelect.setBounds(85, 40, 310, 30);
        contentPane.add(lblSelect);

        lblColumns.setText("Word                        Part of Speech    Meaning");
        lblColumns.setFont(new Font("", Font.BOLD, 18));
        lblColumns.setForeground(new Color(230,150,30));
        lblColumns.setBounds(40,100,620,25);
        contentPane.add(lblColumns);

        btnView.setBounds(610,40,100,30);
        btnView.setFont(new Font("", Font.PLAIN, 16));
        btnView.addActionListener(this);
        contentPane.add(btnView);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource()==btnView) {
            prepareList();
            showList();
        }
    }
}
