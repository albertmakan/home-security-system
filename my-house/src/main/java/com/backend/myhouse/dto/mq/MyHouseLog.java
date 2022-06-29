package com.backend.myhouse.dto.mq;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MyHouseLog {
    private Date timestamp;
    private String level;
    private String message;
}
