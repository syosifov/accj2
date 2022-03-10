package net.yosifov.accounting.accj.utils;

import java.math.BigDecimal;

public class AssgnRecord {
    String debit;
    String credit;
    BigDecimal value;
    BigDecimal ad;
    BigDecimal ac;

    public AssgnRecord() {
    }

    public AssgnRecord(String debit,
                       String credit,
                       BigDecimal value,
                       BigDecimal ad,
                       BigDecimal ac) {
        this.debit = debit;
        this.credit = credit;
        this.value = value;
        this.ad = ad;
        this.ac = ac;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getAd() {
        return ad;
    }

    public void setAd(BigDecimal ad) {
        this.ad = ad;
    }

    public BigDecimal getAc() {
        return ac;
    }

    public void setAc(BigDecimal ac) {
        this.ac = ac;
    }

    @Override
    public String toString() {
        return "AssgnRecord {" +
                "debit='" + debit + '\'' +
                ", credit='" + credit + '\'' +
                ", value=" + value +
                ", ad=" + ad +
                ", ac=" + ac +
                '}';
    }
}
