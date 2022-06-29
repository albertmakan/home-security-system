package com.backend.myhouse.services;

import com.backend.myhouse.model.Message;
import com.backend.myhouse.model.rules.AlarmRule;
import com.backend.myhouse.model.rules.TemplateModel;
import com.backend.myhouse.repository.AlarmRuleRepository;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlarmRuleService {
    private KieSession kieSession;

    @Autowired
    private AlarmRuleRepository alarmRuleRepository;

    public List<AlarmRule> getAll() {
        return alarmRuleRepository.findAll();
    }

    public Message evaluate(Message message) {
        FactHandle handle = kieSession.insert(message);
        kieSession.fireAllRules();
        kieSession.delete(handle); //?
        return message;
    }

    @PostConstruct
    public void createKieSession() {
        List<AlarmRule> alarmRules = getAll();
        List<TemplateModel> data = alarmRules.stream()
                .map(rule -> new TemplateModel(rule.conditionsString(), rule.deviceTypeOrIdString(), rule.getAlarmText()))
                .collect(Collectors.toList());
        ObjectDataCompiler converter = new ObjectDataCompiler();

        String drl = converter.compile(data, AlarmRuleService.class.getResourceAsStream("/alarms/rule-template.drt"));

        KieSession newKS = createKieSessionFromDRL(drl);

        KieSession oldKS = this.kieSession;
        this.kieSession = newKS;
        if (oldKS != null) oldKS.dispose();
    }

    public void save(AlarmRule alarmRule) {
        alarmRuleRepository.save(alarmRule);
    }

    private KieSession createKieSessionFromDRL(String drl) {
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        Results results = kieHelper.verify();
        if (results.hasMessages(org.kie.api.builder.Message.Level.ERROR, org.kie.api.builder.Message.Level.WARNING)) {
            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }
        return kieHelper.build().newKieSession();
    }
}
