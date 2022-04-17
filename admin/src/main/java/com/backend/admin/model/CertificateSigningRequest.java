package com.backend.admin.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "csr")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CertificateSigningRequest {
    @Id
    private String id;

    private String commonName;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String state;
    private String country;
    private String organization;
    private String organizationalUnit;
    private Date endDate;

    private List<String> keyUsageExtensions;

}
