package com.backend.myhouse.services;

import com.backend.myhouse.model.Device;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {
    @Autowired
    private KieSession kieSession;

    public void test() {
        kieSession.insert(new Device());
        kieSession.fireAllRules();
    }
}
