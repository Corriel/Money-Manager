package com.corriel.budget.entity.transaction;

import com.corriel.budget.entity.fund.Fund;
import com.corriel.users.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "money_transaction")
public class MoneyTransaction {

    private long id;
    private BigDecimal amount;
    private Fund fund;
    private User insertingUser;
    private TransactionCategory transactionCategory;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @ManyToOne
    @JoinColumn(name = "fund")
    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    @ManyToOne
    @JoinColumn(name = "inserting_user")
    public User getInsertingUser() {
        return insertingUser;
    }

    public void setInsertingUser(User insertingUser) {
        this.insertingUser = insertingUser;
    }

    @ManyToOne
    @JoinColumn(name = "transaction_category")
    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(TransactionCategory transactionCategory) {
        this.transactionCategory = transactionCategory;
    }
}