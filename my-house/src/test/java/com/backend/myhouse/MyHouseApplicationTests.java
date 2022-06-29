package com.backend.myhouse;

import com.backend.myhouse.model.Device;
import com.backend.myhouse.model.DeviceType;
import com.backend.myhouse.model.Message;
import com.backend.myhouse.model.rules.AlarmRule;
import com.backend.myhouse.model.rules.Condition;
import com.backend.myhouse.model.rules.TemplateModel;
import com.backend.myhouse.services.AlarmRuleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.protocol.types.Field;
import org.bson.types.ObjectId;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.ObjectDataCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class MyHouseApplicationTests {
	@Autowired
	private AlarmRuleService alarmRuleService;

	@Test
	void contextLoads() throws JsonProcessingException {
		KieServices ks = KieServices.Factory.get();
		KieContainer kieContainer = ks.getKieClasspathContainer();
		KieSession kSession = kieContainer.newKieSession("ksession-alarms");

//		ObjectMapper mapper = new ObjectMapper();
//		String str = "{\"abc\":\"aaa\", \"cde\": 42}";
//		TypeReference<Map<String, Object>> typeRef
//				= new TypeReference<Map<String, Object>>() {};
//		Map<String, Object> map = mapper.readValue(str, typeRef);
//		System.out.println(map.get("abc").getClass());
//		System.out.println(map.get("cde").getClass());

		Message m = new Message();
		Map<String, Object> map = new HashMap<>();
		map.put("aaa", 13);
		map.put("bbb", "WWW");
		m.setData(map);
		kSession.insert(m);
		kSession.fireAllRules();

	}

	@Test
	void alarmRule() {
		AlarmRule alarmRule = new AlarmRule();

		Condition c1 = new Condition();
		c1.setField("aaa");
		c1.setOperator(Condition.Operator.GT);
		c1.setValue(10.0);

		Condition c2 = new Condition();
		c2.setField("bbb");
		c2.setOperator(Condition.Operator.EQ);
		c2.setValue("WWW");

		alarmRule.setConditions(Arrays.asList(c1, c2));
		alarmRule.setDeviceType(DeviceType.CAMERA);
		alarmRule.setAlarmText("AAAAAAAAAAAAAAA");
		System.out.println(alarmRule);

		String drl = applyRuleTemplate(alarmRule);
		System.out.println(drl);
		Message m = new Message();
		Map<String, Object> map = new HashMap<>();
		map.put("aaa", 13);
		map.put("bbb", "WWW");
		m.setData(map);
		m.setDeviceId(new ObjectId());
		m.setDeviceType(DeviceType.CAMERA);

		evaluate(drl, m);
	}

	private void evaluate(String drl, Message message) {
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		kieFileSystem.write("src/main/resources/rule.drl", drl);
		kieServices.newKieBuilder(kieFileSystem).buildAll();

		KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
		KieSession kieSession = kieContainer.getKieBase().newKieSession();
		kieSession.insert(message);
		kieSession.fireAllRules();
	}

	private String applyRuleTemplate(AlarmRule rule) {
		List<TemplateModel> data = new ArrayList<>();
		data.add(new TemplateModel(rule.conditionsString(), rule.getDeviceType().toString(), rule.getAlarmText()));

		ObjectDataCompiler converter = new ObjectDataCompiler();

		return converter.compile(data, MyHouseApplicationTests.class.getResourceAsStream("/alarms/rule-template.drt"));
	}

	@Test
	public void ksessionTest() {
		AlarmRule alarmRule = new AlarmRule();
		Condition c1 = new Condition();
		c1.setField("aaa");
		c1.setOperator(Condition.Operator.GT);
		c1.setValue(10.0);
		Condition c2 = new Condition();
		c2.setField("bbb");
		c2.setOperator(Condition.Operator.EQ);
		c2.setValue("WWW");
		alarmRule.setConditions(Arrays.asList(c1, c2));
		alarmRule.setDeviceType(DeviceType.CAMERA);
		alarmRule.setAlarmText("AAAAAAAAAAAAAAA");

		alarmRuleService.save(alarmRule);
		alarmRuleService.createKieSession();

		Message m = new Message();
		Map<String, Object> map = new HashMap<>();
		map.put("aaa", 13);
		map.put("bbb", "WWW");
		m.setData(map);
		m.setDeviceId(new ObjectId());
		m.setDeviceType(DeviceType.CAMERA);

		m = alarmRuleService.evaluate(m);
		System.out.println(m);
	}
}
