package gui;

import domain_models.AccountService;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPage extends JFrame implements ActionListener {

    JLabel welcomeText;

    JLabel nameLabel;
    JTextField name;

    JLabel passwordLabel;
    JPasswordField password;

    JLabel passwordValidationLabel;
    JPasswordField passwordValidation;

    JButton registerButton;

    ButtonGroup group;
    JRadioButton accountTypeOptionChecking;
    JRadioButton accountTypeOptionSavings;

    AccountService accountService;

    SignUpPage(AccountService accountService) {
        this.accountService = accountService;

        welcomeText = new JLabel("Welcome! Please enter you full name and password");
        welcomeText.setBounds(50, 30, 400, 30);

        nameLabel = new JLabel("Full Name:");
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setBounds(40, 95, 120, 30);
        name = new JTextField();
        name.setBounds(170, 95, 230, 30);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setBounds(40, 150, 120, 30);
        password = new JPasswordField();
        password.setBounds(170, 150, 230, 30);

        passwordValidationLabel = new JLabel("Confirm Password:");
        passwordValidationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordValidationLabel.setBounds(40, 205, 120, 30);
        passwordValidation = new JPasswordField();
        passwordValidation.setBounds(170, 205, 230, 30);



        accountTypeOptionChecking = new JRadioButton("Checking");
        accountTypeOptionChecking.setBounds(170, 280, 100, 30);
        accountTypeOptionSavings = new JRadioButton("Savings");
        accountTypeOptionSavings.setBounds(280, 280, 100, 30);

        group = new ButtonGroup();
        group.add(accountTypeOptionChecking);
        group.add(accountTypeOptionSavings);



        registerButton = new JButton("Register");
        registerButton.setBounds(170, 310, 230, 35);
        registerButton.addActionListener(this);

        this.setSize(500, 450);
        this.setTitle("Sign Up Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
        this.add(welcomeText);
        this.add(nameLabel);
        this.add(name);
        this.add(passwordLabel);
        this.add(password);
        this.add(passwordValidationLabel);
        this.add(passwordValidation);
        this.add(registerButton);
        this.add(accountTypeOptionChecking);
        this.add(accountTypeOptionSavings);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == registerButton) {
            String fullNameString = name.getText();

            char[] passwordChars = password.getPassword();
            char[] validationChars = passwordValidation.getPassword();

            if (!java.util.Arrays.equals(passwordChars, validationChars)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String pswrd = String.valueOf(passwordChars);

            if (accountTypeOptionChecking.isSelected()) {
                accountService.createCheckingAccount(fullNameString, pswrd, 100.0);
                JOptionPane.showMessageDialog(this, "Checking Account created successfully!");
            } else {
                accountService.createSavingsAccount(fullNameString, pswrd, 0.05);
                JOptionPane.showMessageDialog(this, "Savings Account created successfully!");
            }
        }
        this.dispose();
    }
}
