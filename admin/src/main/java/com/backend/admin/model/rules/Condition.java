package com.backend.admin.model.rules;

import lombok.Data;

@Data
public class Condition {
    private String field;
    private Object value;
    private Operator operator;

    public enum Operator {
        EQ, NEQ, LT, GT, LTE, GTE
    }
}
