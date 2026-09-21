package gui;

import domain_models.Account;
import domain_models.AccountService;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;

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
    JLabel balance;

    DashboardPage(Account account, AccountService as){
        this.account = account;
        this.as = as;

        name = new JLabel(account.getHolderName());
        name.setBounds(40, 105, 200, 30);

        accountNumber = new JLabel(account.getAccountNumber());
        accountNumber.setBounds(40, 70,  200, 30);

        double balanceDouble =  account.getBalance();
        balance = new JLabel(String.format("%.2f", balanceDouble) + "$");
        balance.setBounds(100, 70, 200, 30);

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
        this.add(balance);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == logout){
            new LoginPage(as);
            System.out.println("The user has logged out");
            this.dispose();
        }

        if(e.getSource() == deposit){
            String option = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
            double amount = Double.parseDouble(option);
            try {
                as.deposit(account.getAccountNumber(), amount);
                this.account = as.getAccount(account.getAccountNumber());
                double balanceNew = this.account.getBalance();
                balance.setText(String.format("%.2f", balanceNew) + "$");

                System.out.println("Fresh balance after deposit: " + this.account.getBalance());
            }catch(InvalidAmountException iae){
                JOptionPane.showMessageDialog(this, "The amount is invalid.\nPleas enter a number grater than 0");
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(this, "The amount is invalid.\nYou can`t enter a letter or a symbol");
            }
        }

        if(e.getSource() == withdraw){
            String option = JOptionPane.showInputDialog(this,
                    "Enter amount to withdraw(pretend like it is an ATM)");
            double amount = Double.parseDouble(option);

            try{
                as.withdraw(account.getAccountNumber(), amount);
                this.account = as.getAccount(account.getAccountNumber());
                double balanceNew = this.account.getBalance();
                balance.setText(String.format("%.2f", balanceNew) + "$");

                System.out.println("Fresh balance after deposit: " + this.account.getBalance());
            }catch(InvalidAmountException iae){
                JOptionPane.showMessageDialog(this, "The amount is invalid.\nPleas enter a number grater than 0");
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(this, "The amount is invalid.\nYou can`t enter a letter or a symbol");
            }
        }
        
        if(e.getSource() == transfer){
            String accountTo = JOptionPane.showInputDialog(this,
                    "Enter account number to transfer: ");
            String amount = JOptionPane.showInputDialog(this,
                    "Enter amount to transfer: ");

            try{
                as.transfer(
                        account.getAccountNumber(), accountTo, Double.parseDouble(amount));

                this.account = as.getAccount(account.getAccountNumber());
                double balanceNew = this.account.getBalance();
                balance.setText(String.format("%.2f", balanceNew) + "$");

            }catch(InsufficientFundsException | InvalidAmountException | AccountNotFoundException | NumberFormatException exception){
                JOptionPane.showMessageDialog(this,
                        "Error occurred and im too lazy to cover all the cases here is the log: " + exception.getMessage());
            }
        }
    }
}
