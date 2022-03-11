package net.yosifov.accounting.accj.utils;

public class ReverseAssignRec {
    private String description;
    private Long ledgerRecId;

    public ReverseAssignRec() {}

    public ReverseAssignRec(String description, Long ledgerRecId) {
        this.description = description;
        this.ledgerRecId = ledgerRecId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLedgerRecId() {
        return ledgerRecId;
    }

    public void setLedgerRecId(Long ledgerRecId) {
        this.ledgerRecId = ledgerRecId;
    }

    @Override
    public String toString() {
        return "ReverseAssignRec {" +
                "description='" + description + '\'' +
                ", ledgerRecId='" + ledgerRecId + '\'' +
                '}';
    }
}
