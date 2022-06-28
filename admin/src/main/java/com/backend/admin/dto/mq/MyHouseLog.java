package com.backend.admin.dto.mq;

import lombok.Data;

import java.util.Date;

@Data
public class MyHouseLog {
    private Date timestamp;
    private String level;
    private String message;
}
