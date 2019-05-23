package Viewer.LoginFrame;

import Database.DBHandler;
import Database.DBUtil;
import MyComponents.MyButton;
import MyComponents.MyLabel;
import Viewer.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginFrame extends JFrame implements ActionListener{
    
    private JPanel contentPane = new JPanel();
    private MyLabel lbl = new MyLabel("Vocabulary Check System");
    private JLabel lblProgrammer = new JLabel("implementing Mysql         Produced by WHU 2016300030043  2017/12/5");
    private MyLabel lblInfo = new MyLabel("Connecting to mysql database:");
    private MyLabel lblUser = new MyLabel("User name of the mysql connection:");
    private MyLabel lblPassword = new MyLabel("Password of the mysql connection:");
    private JTextField txtUser = new JTextField();
    private JTextField txtPassword = new JTextField();
    private MyButton btnLogin = new MyButton("Connect");
    private LoginFrame self = this;
    
    public LoginFrame() {
        
        this.setTitle("Database connection");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(190,160,220));
        setContentPane(contentPane);
        setVisible(true);

        lbl.setBounds(50,25,500,50);
        lbl.setFont(new Font("", Font.ITALIC|Font.BOLD, 40));
        contentPane.add(lbl);

        lblProgrammer.setBounds(60, 80,500,20);
        lblProgrammer.setForeground(new Color(255,255,0));
        lblProgrammer.setFont(new Font("", Font.PLAIN, 15));
        contentPane.add(lblProgrammer);
        
        lblInfo.setBounds(100, 110,400,30);
        lblInfo.setFont(new Font("", Font.PLAIN, 20));
        contentPane.add(lblInfo);

        lblUser.setBounds(100,160,400,30);
        lblUser.setFont(new Font("",Font.PLAIN,20));
        contentPane.add(lblUser);

        txtUser.setBounds(100,205,400,30);
        txtUser.setFont(new Font("", Font.PLAIN,20));
        contentPane.add(txtUser);

        lblPassword.setBounds(100,260,400,30);
        lblPassword.setFont(new Font("",Font.PLAIN,20));
        contentPane.add(lblPassword);

        txtPassword.setBounds(100,305,400,30);
        txtPassword.setFont(new Font("", Font.PLAIN,20));
        contentPane.add(txtPassword);

        btnLogin.setBounds(220,370,160,50);
        btnLogin.addActionListener(this);
        contentPane.add(btnLogin);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(self, "Are you sure to exit?")==0) {
                    System.exit(0);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = txtUser.getText();
        String password = txtPassword.getText();
        DBUtil.setUser(user);
        DBUtil.setPassword(password);
        if (DBUtil.getConn()==null) {
            JOptionPane.showMessageDialog(self, "Error: Invalid User or Password\nOr mysql driver not found!");
            txtUser.setText("");
            txtPassword.setText("");
        } else {
            MainFrame mainFrame = new MainFrame();
            this.dispose();
        }
    }
}
