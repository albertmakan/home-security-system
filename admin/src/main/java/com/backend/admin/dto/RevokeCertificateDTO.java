package com.backend.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevokeCertificateDTO {
    private String serialNumber;
    private ObjectId revokerId;
    private String reason;
}
