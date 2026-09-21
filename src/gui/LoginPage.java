package gui;


import domain_models.Account;
import domain_models.AccountService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame implements ActionListener {
    JButton enter;
    JButton signUp;
    JLabel label;
    JTextField login;
    JPasswordField password;
    private final AccountService accountService;

    public LoginPage(AccountService accountService){
        this.accountService = accountService;

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
        this.setVisible(true);
        this.add(label);
        this.add(enter);
        this.add(login);
        this.add(password);
        this.add(signUp);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == enter){
            System.out.println("User name input is: " + login.getText());

            String loginStr = login.getText();
            String passwordStr = new String(password.getPassword());

            if(loginStr.isEmpty() || passwordStr.isEmpty()){
                JOptionPane.showMessageDialog(this, "The login or password are not entered");
                return;
            }

            try{
                Account temp = accountService.getAccount(loginStr);

                    if(passwordStr.equals(temp.getPassword())){

                        DashboardPage dash = new DashboardPage(temp, accountService);
                        this.dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Password is incorrect");
                    }


            }catch(Exception exception){
                JOptionPane.showMessageDialog(this, "Account was not found");
            }

        } else if (e.getSource() == signUp) {
            System.out.println("Sign Up clicked");

            SignUpPage sup = new SignUpPage(this.accountService);
            this.dispose();
        }
    }
}
