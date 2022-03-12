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
    private BigDecimal assets = BigDecimal.ZERO;
    @Column(precision = 19, scale = C.SCALE)
    private BigDecimal am = BigDecimal.ZERO;


    @Column(precision = 19, scale = C.SCALE)
    private BigDecimal liabilities = BigDecimal.ZERO;
    @Column(precision = 19, scale = C.SCALE)
    private BigDecimal lm = BigDecimal.ZERO;

    @Column(precision = 19, scale = C.SCALE)
    private BigDecimal balance = BigDecimal.ZERO;
    @Column(precision = 19, scale = C.SCALE)
    private BigDecimal bm = BigDecimal.ZERO;

    @Column
    @NotNull
    private LocalDate lastModified;

    @Column
    @NotNull
    private AT at = AT.A;

    @Column
    private String description = "";

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    private Account parentAccount;

    @OneToMany(mappedBy = "parentAccount", fetch = FetchType.LAZY)
    private List<Account> childrenAccounts;

    public Account() {
    }

    public Account(String id,
                   BigDecimal assets,
                   BigDecimal am,
                   BigDecimal liabilities,
                   BigDecimal lm,
                   BigDecimal balance,
                   BigDecimal bm,
                   LocalDate lastModified,
                   AT at,
                   String description,
                   Account parentAccount,
                   List<Account> childrenAccounts) {
        this.id = id;
        this.assets = assets;
        this.am = am;
        this.liabilities = liabilities;
        this.lm = lm;
        this.balance = balance;
        this.bm = bm;
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
            case A -> {
                balance = assets.subtract(liabilities);
                bm = am.subtract(lm);
            }
            case L -> {
                balance = liabilities.subtract(assets);
                bm = lm.subtract(am);
            }
            default -> {
                balance = assets.subtract(liabilities);
                bm = am.subtract(lm);
            }
        }

    }

    public void debit(BigDecimal v,
                      BigDecimal m) {
        assets = assets.add(v);
        am = am.add(m);
        recap();
    }

    public void credit(BigDecimal v,
                       BigDecimal m) {
        liabilities = liabilities.add(v);
        lm = lm.add(m);
        recap();
    }

    public BigDecimal getAm() {
        return am;
    }

    public void setAm(BigDecimal am) {
        this.am = am;
    }

    public BigDecimal getLm() {
        return lm;
    }

    public void setLm(BigDecimal lm) {
        this.lm = lm;
    }

    public BigDecimal getBm() {
        return bm;
    }

    public void setBm(BigDecimal bm) {
        this.bm = bm;
    }

    @Override
    public String toString() {
        return "Account {" +
                "id='" + id + '\'' +
                ", assets=" + assets +
                ", am=" + am +
                ", liabilities=" + liabilities +
                ", lm=" + lm +
                ", balance=" + balance +
                ", bm=" + bm +
                ", lastModified=" + lastModified +
                ", at=" + at +
                ", description='" + description + '\'' +
                ", parentAccount=" + parentAccount +
                ", childrenAccounts=" + childrenAccounts +
                '}';
    }
}
