package com.backend.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDTO {
    private String issuer;
    private String subject;
    private Date expirationDate;
}
