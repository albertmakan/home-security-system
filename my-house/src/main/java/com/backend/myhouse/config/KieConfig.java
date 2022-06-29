package com.backend.myhouse.config;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class KieConfig {

    @Bean
    public KieContainer kieContainer() {
        KieServices ks = KieServices.Factory.get();
        return ks.getKieClasspathContainer();
    }

    @Bean(name = "rulesSession")
    public KieSession rulesSession() {
        return kieContainer().newKieSession("rulesSession");
    }

    @Bean(name = "cepSession")
    public KieSession eventsSession() {
        KieSession kieSession = this.kieContainer().newKieSession("cepSession");
        return kieSession;
    }
}

