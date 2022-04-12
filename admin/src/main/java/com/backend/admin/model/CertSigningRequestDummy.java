package com.backend.admin.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CertSigningRequestDummy {

    private String firstName;

    private String lastName;

    private String email;

    private String country;

    private String organisation;

    private String organisationUnit;

    private String extension;

    private boolean certificateAuthority;

    private boolean rootCert;

    private String issuerEmail;

    private Date endDate;

    // TODO idk if these are just some extensions? 
    // Check if there are others that need to be added

    private boolean digitalSignature;

    private boolean nonRepudiation;

    private boolean keyAgreement;

    private boolean keyEncipherment;


}
