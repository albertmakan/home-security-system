package com.backend.admin.model;

import java.security.PrivateKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.x500.X500Name;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssuerData {
    private X500Name x500name;
    private PrivateKey privateKey;
}
