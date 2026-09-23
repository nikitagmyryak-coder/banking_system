package gui;

import domain_models.Account;
import domain_models.AccountService;
import domain_models.Transaction;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionHistoryPage extends JFrame implements ActionListener{
    Account account;
    AccountService as;
    JLabel tableName;
    JButton back;
    JList<Transaction> transactionList;
    JScrollPane scrollPane;

    TransactionHistoryPage(Account account, AccountService as){
        this.account = account;
        this.as = as;
        this.transactionList = new JList<>(as.getTransactionHistory(account.getAccountNumber())
                        .toArray(new Transaction[0]));
        this.scrollPane = new JScrollPane(transactionList);

        scrollPane.setBounds(40, 60, 400, 280);
        this.add(scrollPane);

        tableName = new JLabel("Your transaction history");
        tableName.setBounds(40, 20, 300, 30);
        this.add(tableName);

        back = new JButton("Back");
        back.setBounds(40, 370, 100, 30);
        back.addActionListener(this);
        this.add(back);

        this.setSize(500, 450);
        this.setTitle("Transaction History");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back){
            this.dispose();
        }
    }
}
