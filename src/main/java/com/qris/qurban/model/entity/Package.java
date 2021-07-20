package com.qris.qurban.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PACKAGE")
public class Package extends AuditModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PACKAGE_ID", nullable = false, updatable = false)
    private Long packageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RECIPIENT_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Recipient recipient;

    @NotNull
    @Size(max = 100)
    @Column(name = "QR_NAME", nullable = false)
    private String qrName;

    @NotNull
    @Column(name = "QR_SENT_STATUS", nullable = false)
    private boolean qrSentStatus;

    @NotNull
    @Column(name = "PACKAGE_RECEIVED_STATUS", nullable = false)
    private boolean packageReceivedStatus;

    public Long getPackageId()
    {
        return packageId;
    }

    public void setPackageId(Long packageId)
    {
        this.packageId = packageId;
    }

    public Recipient getRecipient()
    {
        return recipient;
    }

    public void setRecipient(Recipient recipient)
    {
        this.recipient = recipient;
    }

    public String getQrName()
    {
        return qrName;
    }

    public void setQrName(String qrName)
    {
        this.qrName = qrName;
    }

    public boolean isQrSentStatus()
    {
        return qrSentStatus;
    }

    public void setQrSentStatus(boolean qrSentStatus)
    {
        this.qrSentStatus = qrSentStatus;
    }

    public boolean isPackageReceivedStatus()
    {
        return packageReceivedStatus;
    }

    public void setPackageReceivedStatus(boolean packageReceivedStatus)
    {
        this.packageReceivedStatus = packageReceivedStatus;
    }
}
