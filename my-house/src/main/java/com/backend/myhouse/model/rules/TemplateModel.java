package com.backend.myhouse.model.rules;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TemplateModel {
    private String conditions, deviceTypeOrId, alarmText;
}
