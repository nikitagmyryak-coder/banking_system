package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {
    JButton enter;

    public MyFrame(){

        JLabel login = new JLabel();
        login.setText("Login");
        login.setHorizontalAlignment(SwingConstants.CENTER);
        login.setVerticalAlignment(SwingConstants.CENTER);
        login.setBounds(200, 40, 80, 10);

        enter = new JButton("Enter");
        enter.setBounds(200, 100, 100, 50);
        enter.addActionListener(this);


        this.setSize(500, 500);
        this.setTitle("Banking System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.add(login);
        this.add(enter);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == enter){
            System.out.println("popa");
        }
    }
}
