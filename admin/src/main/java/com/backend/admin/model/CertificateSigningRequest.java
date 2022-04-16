package com.backend.admin.model;

import java.util.Date;

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
    private String country;
    private String organisation;
    private String organisationalUnit;
    private Date endDate;

    // TODO idk if these are just some extensions?
    // Check if there are others that need to be added

    private boolean certificateAuthority; // we probably won't be using this one
    private boolean rootCert; // we probably won't be using this one
    private boolean digitalSignature;
    private boolean nonRepudiation;
    private boolean keyAgreement;
    private boolean keyEncipherment;

    // signingCertificate se podrazumeva kada je CA - koristiti kada se javni kljuc
    // subjekta koristi za verifikaciju potpisa na sertifikatima

}
