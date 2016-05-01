package com.corriel.budget.entity.transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "transaction_category")
public class TransactionCategory {

    private long id;
    private String name;
    private Set<MoneyTransaction> moneyTransactions;
    private BigDecimal limit;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(length = 120, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "transactionCategory")
    public Set<MoneyTransaction> getMoneyTransactions() {
        return moneyTransactions;
    }

    public void setMoneyTransactions(Set<MoneyTransaction> moneyTransactions) {
        this.moneyTransactions = moneyTransactions;
    }

    @Column(name = "money_limit")
    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }
}