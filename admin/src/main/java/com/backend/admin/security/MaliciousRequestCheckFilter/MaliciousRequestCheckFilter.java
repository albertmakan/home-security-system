package com.backend.admin.security.MaliciousRequestCheckFilter;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

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

    private List<String> maliciousIPAddresses = Arrays.asList(
            "82.117.204.174",
            "93.184.81.106",
            "91.148.114.25",
            "89.216.97.186",
            "91.150.102.224",
            "94.189.142.212",
            "95.85.160.34",
            "188.2.61.180",
            "87.116.173.253",
            "95.85.188.119",
            "91.150.104.29",
            "79.175.107.175",
            "87.116.172.244",
            "94.189.162.248",
            "87.116.172.59",
            "213.198.212.215",
            "217.16.133.167",
            "188.2.22.112",
            "194.247.192.229",
            "194.247.192.232",
            "94.189.221.145",
            "94.189.145.141",
            "217.16.139.57",
            "79.101.99.214",
            "79.101.50.88");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        AppRequest appRequest = new AppRequest(request.getRemoteAddr(), request.getRequestURI());
        int firedRules = 0;
        int firedCep = 0;

        InetAddress inetAddress = InetAddress.getByName(request.getRemoteAddr());
        if (inetAddress instanceof Inet4Address) {
            rulesSession.getAgenda().getAgendaGroup("requests").setFocus();
            rulesSession.setGlobal("maliciousIPAddresses", maliciousIPAddresses);
            rulesSession.insert(appRequest);
            firedRules = rulesSession.fireAllRules();
        }

        if (firedRules > 0){
            log.error("-----------MALICIOUS IP ADDRESS! REQUEST BLOCKED-----------");
            return;
        }

        eventsSession.getAgenda().getAgendaGroup("requests").setFocus();
        eventsSession.insert(appRequest);
        firedCep = eventsSession.fireAllRules();

        if (firedCep > 0){
            log.error("-----------SPAMMING IP ADDRESS! REQUEST BLOCKED-----------");
            return;
        }

        filterChain.doFilter(request, response);

    }

}