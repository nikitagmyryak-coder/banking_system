package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {
    JButton enter;
    JButton signUp;
    JLabel label;
    JTextField login;
    JPasswordField password;

    public MyFrame(){

        label = new JLabel();
        label.setText("Login");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBounds(150, 60, 200, 30);

        enter = new JButton("Enter");
        enter.setBounds(150, 205, 200, 35);
        enter.addActionListener(this);

        signUp = new JButton("Sigh Up");
        signUp.setBounds(150, 250, 200, 35);
        signUp.addActionListener(this);

        login = new JTextField();
        login.setBounds(150, 90, 200, 35);

        password = new JPasswordField();
        password.setBounds(150, 145, 200, 35);

        this.setSize(500, 500);
        this.setTitle("Banking System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.add(label);
        this.add(enter);
        this.add(login);
        this.add(password);
        this.add(signUp);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == enter){
            System.out.println("Enter clicked");
        } else if (e.getSource() == signUp) {
            System.out.println("Sign Up clicked");
        }
    }
}
