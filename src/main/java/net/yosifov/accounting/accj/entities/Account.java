package net.yosifov.accounting.accj.entities;

import com.sun.istack.Nullable;
import net.yosifov.accounting.accj.utils.AT;
import net.yosifov.accounting.accj.utils.C;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private String id;

    @Column(precision = 19, scale = C.SCALE)
    @NotNull
    private BigDecimal assets;

    @Column(precision = 19, scale = C.SCALE)
    @NotNull
    private BigDecimal liabilities;

    @Column(precision = 19, scale = C.SCALE)
    @NotNull
    private BigDecimal balance;

    @Column
    @NotNull
    private LocalDate lastModified;

    @Column
    @NotNull
    private AT at;

    @Column
    @NotNull
    private String description;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    private Account parentAccount;

    @OneToMany(mappedBy = "parentAccount", fetch = FetchType.LAZY)
    private List<Account> childrenAccounts;

    public Account() {
        this.assets = new BigDecimal("0.00");
        this.liabilities = new BigDecimal("0.00");
        this.balance = new BigDecimal("0.00");
        this.at = AT.A;
        this.description = "";
    }

    public Account(String id,
                   BigDecimal assets,
                   BigDecimal liabilities,
                   BigDecimal balance,
                   LocalDate lastModified,
                   AT at,
                   String description,
                   Account parentAccount,
                   List<Account> childrenAccounts) {
        this();
        this.id = id;
        this.assets = assets;
        this.liabilities = liabilities;
        this.balance = balance;
        this.lastModified = lastModified;
        this.at = at;
        this.description = description;
        this.parentAccount = parentAccount;
        this.childrenAccounts = childrenAccounts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAssets() {
        return assets;
    }

    public void setAssets(BigDecimal assets) {
        this.assets = assets;
    }

    public BigDecimal getLiabilities() {
        return liabilities;
    }

    public void setLiabilities(BigDecimal liabilities) {
        this.liabilities = liabilities;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public AT getAt() {
        return at;
    }

    public void setAt(AT at) {
        this.at = at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getParentAccount() {
        return parentAccount;
    }

    public void setParentAccount(Account parentAccount) {
        this.parentAccount = parentAccount;
    }

    public List<Account> getChildrenAccounts() {
        return childrenAccounts;
    }

    public void setChildrenAccounts(List<Account> childrenAccounts) {
        this.childrenAccounts = childrenAccounts;
    }

    protected void recap() {
        switch (at) {
            case A -> balance = assets.subtract(liabilities);
            case L -> balance = liabilities.subtract(assets);
            default -> balance = assets.subtract(liabilities);
        }

    }

    public void debit(BigDecimal v){
        assets = assets.add(v);
        recap();
    }

    public void credit(BigDecimal v){
        liabilities = liabilities.add(v);
        recap();
    }

    @Override
    public String toString() {
        return "Account {" +
                "id='" + id + '\'' +
                ", assets=" + assets +
                ", liabilities=" + liabilities +
                ", balance=" + balance +
                ", lastModified=" + lastModified +
                ", at=" + at +
                ", description='" + description + '\'' +
                ", parentAccount=" + parentAccount +
                ", childrenAccounts=" + childrenAccounts +
                '}';
    }
}
