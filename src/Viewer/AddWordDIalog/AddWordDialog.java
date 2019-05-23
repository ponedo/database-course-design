package Viewer.AddWordDIalog;

import Database.DBHandler;
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

public class AddWordDialog extends JDialog implements ActionListener{

    private JPanel contentPane = new JPanel();
    private MyLabel lbl_input = new MyLabel("Input the new word here (づ￣ 3￣)づ :");
    private MyLabel lblPosSelect = new MyLabel("What kind of word?");
    private MyLabel lblMeaning = new MyLabel("What does the word mean?");
    private JTextField txtWord = new JTextField();
    private JTextField txtMeaning = new JTextField();
    private JComboBox posSelect = new JComboBox<String>(new String[]{"n.","v.","adj.","adv.","pron.","num.","art.","prep.","conj.","itrj."});
    private MyButton btnAdd = new MyButton("Add");
    private MyButton btnCancel = new MyButton("Cancel");

    private DBHandler DBhandler = new DBHandler();

    private MainFrame parent;

    public AddWordDialog(MainFrame parent, boolean parentOperatable) {

        super(parent, parentOperatable);
        this.parent = parent;

        this.setTitle("Add a word");
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 400, 450);
        setResizable(false);
        setLocationRelativeTo(parent);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(194,162,227));
        setContentPane(contentPane);
        setVisible(true);

        lbl_input.setBounds(50,50,300,30);
        lbl_input.setFont(new Font("", Font.BOLD, 16));
        contentPane.add(lbl_input);

        txtWord.setBounds(50,90,300,30);
        txtWord.setFont(new Font("", Font.PLAIN, 20));
        contentPane.add(txtWord);

        lblPosSelect.setBounds(50,150,150,30);
        lblPosSelect.setFont(new Font("", Font.BOLD, 16));
        contentPane.add(lblPosSelect);

        posSelect.setBounds(50,190,100,30);
        posSelect.setFont(new Font("", Font.PLAIN, 20));
        contentPane.add(posSelect);

        lblMeaning.setBounds(50,250,220,30);
        lblMeaning.setFont(new Font("", Font.BOLD, 16));
        contentPane.add(lblMeaning);

        txtMeaning.setBounds(50,290,300,30);
        txtMeaning.setFont(new Font("", Font.PLAIN, 20));
        contentPane.add(txtMeaning);

        btnAdd.setBounds(70,350,90,30);
        btnAdd.setFont(new Font("", Font.PLAIN, 16));
        btnAdd.addActionListener(this);
        contentPane.add(btnAdd);
        getRootPane().setDefaultButton(btnAdd);

        btnCancel.setBounds(230,350,90,30);
        btnCancel.setFont(new Font("", Font.PLAIN, 16));
        btnCancel.addActionListener(this);
        contentPane.add(btnCancel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DBhandler.closeAll();
                dispose();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource()==btnAdd) {
            String word = txtWord.getText();
            String pos = (String)posSelect.getSelectedItem();
            String meaning = txtMeaning.getText();

            if (JOptionPane.showConfirmDialog(this,
                    "Add \n\""+ word + " " + pos + " " + meaning +"\"\n to the database?")==0) {
                JOptionPane.showMessageDialog(this, "Successfully Added!");
                DBhandler.addWord(word, pos, meaning);
                posSelect.setSelectedIndex(0);
                txtMeaning.setText("");
                parent.updateNumbers();
            }
        } else if (ae.getSource()==btnCancel) {
            DBhandler.closeAll();
            dispose();
        }
    }
}
