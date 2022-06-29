package com.backend.admin.security.MaliciousRequestCheckFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.admin.model.request.AppRequest;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@NoArgsConstructor
public class MaliciousRequestCheckFilter extends OncePerRequestFilter {

    @Setter
    private KieSession rulesSession;

    @Setter
    private KieSession eventsSession;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // log.info("REQUEST RECEIVED ------ " + request.getRequestURI());
        
        AppRequest appRequest = new AppRequest(request.getRemoteAddr(), request.getRequestURI());

        rulesSession.getAgenda().getAgendaGroup("requests").setFocus();
        rulesSession.insert(appRequest);
        rulesSession.fireAllRules();

        eventsSession.getAgenda().getAgendaGroup("requests").setFocus();
        eventsSession.insert(appRequest);
        eventsSession.fireAllRules();

        filterChain.doFilter(request, response);
        
    }
 
}