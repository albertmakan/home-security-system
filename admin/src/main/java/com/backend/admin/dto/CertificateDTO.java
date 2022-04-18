package com.backend.admin.dto;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.backend.admin.model.enums.CertificateType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDTO {
    private String issuer;
    private String subject;
    private Date expirationDate;
    private String serialNumber;
    private String alias;
    private String subjectEmail;
    private CertificateType type;

    public CertificateDTO(String issuer, String subject, Date expirationDate) {
        this.issuer = issuer;
        this.subject = subject;
        this.expirationDate = expirationDate;
    }

}
