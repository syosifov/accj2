package net.yosifov.accounting.accj.entities;

import com.sun.istack.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class LedgerRec {

    @Id
    @GeneratedValue
    private Long id;

    private Long recId;

    private BigDecimal amount;

    private LocalDateTime localDateTime;

    private String description;

    private Integer fiscalYear;

    @OneToMany(mappedBy = "ledgerRec", fetch = FetchType.LAZY)
    private List<LedgerRecDetail> lstLedgerRecDetail;

    @Nullable
    @OneToOne
    private LedgerRec refLedgerRec;

    public LedgerRec() {
        amount = BigDecimal.ZERO;
        localDateTime = LocalDateTime.now();
        recId = 0L;
        fiscalYear = localDateTime.getYear();
    }

    public LedgerRec(Long recId,
                     BigDecimal amount,
                     LocalDateTime localDateTime,
                     String description,
                     Integer fiscalYear,
                     List<LedgerRecDetail> lstLedgerRecDetail,
                     LedgerRec refLedgerRec) {
        this.recId = recId;
        this.amount = amount;
        this.localDateTime = localDateTime;
        this.description = description;
        this.fiscalYear = fiscalYear;
        this.lstLedgerRecDetail = lstLedgerRecDetail;
        this.refLedgerRec = refLedgerRec;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public LedgerRec getRefLedgerRec() {
        return refLedgerRec;
    }

    public void setRefLedgerRec(LedgerRec refLedgerRec) {
        this.refLedgerRec = refLedgerRec;
    }

    public List<LedgerRecDetail> getLstLedgerRecDetail() {
        return lstLedgerRecDetail;
    }

    public void setLstLedgerRecDetail(List<LedgerRecDetail> lstLedgerRecDetail) {
        this.lstLedgerRecDetail = lstLedgerRecDetail;
    }

    @Override
    public String toString() {
        return "LedgerRec{" +
                "id=" + id +
                ", recId=" + recId +
                ", amount=" + amount +
                ", localDateTime=" + localDateTime +
                ", description='" + description + '\'' +
                ", fiscalYear=" + fiscalYear +
                '}';
    }


}
