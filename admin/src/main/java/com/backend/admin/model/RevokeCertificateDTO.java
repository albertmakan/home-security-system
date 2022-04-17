package com.backend.admin.model;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class RevokeCertificateDTO {
    private String serialNumber;
    private ObjectId revokerId;
    private String reason;
}
