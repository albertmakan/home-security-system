package com.backend.admin.model.cert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Revocation {
    private String serialNumber;
    private ObjectId revokerId;
    private Date timeStamp;
    private String reason;
}
