package com.backend.myhouse.model.rules;

import com.backend.myhouse.model.DeviceType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Data
@Document
public class AlarmRule {
    @Id @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private DeviceType deviceType;
    private List<Condition> conditions;
    private String alarmText;

    public String conditionsString() {
        StringBuilder statementBuilder = new StringBuilder();
        for (Condition condition : getConditions()) {
            String operator = null;
            switch (condition.getOperator()) {
                case EQ:
                    operator = "==";
                    break;
                case NEQ:
                    operator = "!=";
                    break;
                case GT:
                    operator = ">";
                    break;
                case LT:
                    operator = "<";
                    break;
                case GTE:
                    operator = ">=";
                    break;
                case LTE:
                    operator = "<=";
                    break;
            }
            statementBuilder.append("data.get('").append(condition.getField()).append("') ").append(operator).append(" ");
            if (condition.getValue() instanceof String) {
                statementBuilder.append("'").append(condition.getValue()).append("'");
            } else {
                statementBuilder.append(condition.getValue());
            }
            statementBuilder.append(", ");
        }
        String statement = statementBuilder.toString();
        return statement.substring(0, statement.length() - 2);
    }
}
