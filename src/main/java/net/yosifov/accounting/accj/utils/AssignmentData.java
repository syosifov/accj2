package net.yosifov.accounting.accj.utils;

import java.math.BigDecimal;
import java.util.List;

public class AssignmentData {
    private String description;
    private List<AssgnRecord> lstAssgn;
    private BigDecimal amount;

    public AssignmentData() {
    }

    public AssignmentData(String description,
                          List<AssgnRecord> lstAssgn,
                          BigDecimal amount) {
        this.description = description;
        this.lstAssgn = lstAssgn;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AssgnRecord> getLstAssgn() {
        return lstAssgn;
    }

    public void setLstAssgn(List<AssgnRecord> lstAssgn) {
        this.lstAssgn = lstAssgn;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "AssignmentData {" +
                "description='" + description + '\'' +
                ", lstAssgn=" + lstAssgn +
                ", amount=" + amount +
                '}';
    }
}
