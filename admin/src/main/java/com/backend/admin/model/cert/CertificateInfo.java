package com.backend.admin.model.cert;

import com.backend.admin.model.cert.enums.CertificateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class CertificateInfo {
    @Id
    private ObjectId id;
    private String serialNumber;
    private String alias;
    private String subjectEmail;
    private CertificateType type;
    private Revocation revocation;
}
