package gui;

import domain_models.Account;
import domain_models.AccountService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardPage extends JFrame implements ActionListener {

    Account account;
    AccountService as;
    JLabel name;
    JLabel accountNumber;
    JButton logout;
    JButton deposit;
    JButton withdraw;
    JButton transfer;

    DashboardPage(Account account, AccountService as){
        this.account = account;
        this.as = as;

        name = new JLabel(account.getHolderName());
        name.setBounds(40, 105, 200, 30);

        accountNumber = new JLabel(account.getAccountNumber());
        accountNumber.setBounds(40, 70,  200, 30);

        logout = new JButton("Logout");
        logout.setBounds(40, 350, 120, 30);
        logout.addActionListener(this);

        deposit = new JButton("Deposit");
        deposit.setBounds(40, 150, 120, 30);
        deposit.addActionListener(this);

        withdraw = new JButton("Withdraw");
        withdraw.setBounds(40, 190, 120, 30);
        withdraw.addActionListener(this);

        transfer = new JButton("Transfer");
        transfer.setBounds(40, 230, 120, 30);
        transfer.addActionListener(this);

        this.setSize(500, 450);
        this.setTitle(account.getAccountNumber());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
        this.add(name);
        this.add(accountNumber);
        this.add(logout);
        this.add(deposit);
        this.add(withdraw);
        this.add(transfer);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == logout){
            new LoginPage(as);
            System.out.println("The user has logged out");
            this.dispose();
        }
    }
}
