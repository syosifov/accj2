package net.yosifov.accounting.accj.utils;

import java.math.BigDecimal;

public class AssgnRecord {
    String debit;
    String credit;
    BigDecimal value;
    BigDecimal vm;


    public AssgnRecord() {
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

    public BigDecimal getVm() {
        return vm;
    }

    public void setVm(BigDecimal vm) {
        this.vm = vm;
    }

    @Override
    public String toString() {
        return "AssgnRecord {" +
                "debit='" + debit + '\'' +
                ", credit='" + credit + '\'' +
                ", value=" + value +
                ", vm=" + vm +
                '}';
    }
}
