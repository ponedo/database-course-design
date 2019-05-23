package Viewer.AlterWordDialog;

import Database.DBHandler;
import Entity.WordlistRecord;
import Viewer.MainFrame;
import MyComponents.MyButton;
import MyComponents.MyLabel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AlterWordDialog extends JDialog implements ActionListener{

    private JPanel contentPane = new JPanel();
    private MyLabel lblWord = new MyLabel("Input the word here (づ￣ 3￣)づ :");
    private JTextField txtWord = new JTextField();
    private DefaultListModel<String> model = new DefaultListModel<String>();
    private JList<String> displayList = new JList<String>();
    private JScrollPane displayPane = new JScrollPane(displayList);
    private MyButton btnConfirm = new MyButton("Check");
    private MyButton btnDelete = new MyButton("Delete");
    private MyButton btnCancel = new MyButton("Cancel");
    private MainFrame parent;
    private DBHandler DBhandler = new DBHandler();

    private ArrayList<WordlistRecord> records;
    private boolean deleteOK = false;

    private void showRecords() {
        String word;
        String pos;
        String meaning;
        records = DBhandler.getRecords(txtWord.getText());

        int recNum = records.size();
        if (recNum==0) {
            model.addElement("No records of " + txtWord.getText());
            deleteOK = false;
        } else {
            deleteOK = true;
            for (int i = 0; i < recNum; i++) {
                //String to add to the display list
                String line;
                //Get the record
                WordlistRecord record = records.get(i);
                word = record.getWord();
                pos = record.getPos();
                meaning = record.getMeaning();
                //Alignment
                String alignment1 = "";
                int alignment1_num = 20 - word.length();
                for (int k = 0; k < alignment1_num; k++) {
                    alignment1 = alignment1 + " ";
                }
                String alignment2 = "";
                int alinment2_num = 8 - pos.length();
                for (int k = 0; k < alinment2_num; k++) {
                    alignment2 = alignment2 + " ";
                }
                //Compose the line for printing
                line = word + alignment1 + pos + alignment2 + meaning;
                model.addElement(line);
            }
        }
        displayList.setModel(model);
    }

    public AlterWordDialog(MainFrame parent, boolean parentOperatable) {

        super(parent, parentOperatable);
        this.parent = parent;

        this.setTitle("Alter word info");
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 800, 450);
        setResizable(false);
        setLocationRelativeTo(parent);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(70,70,70));
        setContentPane(contentPane);
        setVisible(true);

        lblWord.setBounds(50,30,280,30);
        lblWord.setFont(new Font("", Font.BOLD, 17));
        lblWord.setForeground(new Color(250,250,230));
        contentPane.add(lblWord);

        txtWord.setBounds(350,30,260,30);
        txtWord.setFont(new Font("", Font.BOLD, 16));
        contentPane.add(txtWord);

        btnConfirm.setBounds(630,30,120,30);
        btnConfirm.setFont(new Font("", Font.PLAIN, 16));
        btnConfirm.addActionListener(this);
        contentPane.add(btnConfirm);
        contentPane.getRootPane().setDefaultButton(btnConfirm);

        btnDelete.setBounds(260,350,100,30);
        btnDelete.setFont(new Font("", Font.PLAIN, 16));
        btnDelete.addActionListener(this);
        contentPane.add(btnDelete);
        getRootPane().setDefaultButton(btnDelete);

        btnCancel.setBounds(420,350,100,30);
        btnCancel.setFont(new Font("", Font.PLAIN, 16));
        btnCancel.addActionListener(this);
        contentPane.add(btnCancel);

        TitledBorder border = new TitledBorder("Delete a record you added before");
        border.setTitleFont(new Font("", Font.ROMAN_BASELINE, 18));
        border.setTitleColor(new Color(230,150,30));
        border.setBorder(BorderFactory.createLineBorder(Color.white, 3));

        displayList.setFont(new Font("DialogInput", Font.BOLD, 18));
        displayList.setBackground(new Color(55,55,55));
        displayList.setForeground(new Color(255,255,255));
        displayPane.setBounds(40,90, 720,240);
        displayPane.setBorder(border);
        displayPane.setBackground(new Color(55,55,55));
        contentPane.add(displayPane);

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
        if (ae.getSource()==btnConfirm) {
            model.removeAllElements();
            displayList.setModel(model);
            if (records!=null) {
                records.clear();
            }
            deleteOK=false;
            showRecords();
        } else if (ae.getSource()== btnDelete) {
            if (!deleteOK) {
                JOptionPane.showMessageDialog(this,
                        "Error: Fail to delete.\nCheck the input or select a record.");
            } else {
                int indexSelected = displayList.getSelectedIndex();
                WordlistRecord recordSelected = records.get(indexSelected);
                String word = recordSelected.getWord();
                String pos = recordSelected.getPos();
                String meaning = recordSelected.getMeaning();
                String message = (String) displayList.getSelectedValue();
                if (JOptionPane.showConfirmDialog(this,
                        "Delete\n" + message + "?") == 0) {
                    JOptionPane.showMessageDialog(this, "Successfully deleted!");
                    DBhandler.deleteRecord(word, pos, meaning);
                    model.removeAllElements();
                    displayList.setModel(model);
                    records.clear();
                    deleteOK=false;
                    parent.updateNumbers();
                }
            }
        } else if (ae.getSource()==btnCancel) {
            DBhandler.closeAll();
            dispose();
        }
    }
}
