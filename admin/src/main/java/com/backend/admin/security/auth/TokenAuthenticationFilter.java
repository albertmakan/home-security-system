package com.backend.admin.security.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import com.backend.admin.util.TokenUtils;

// Filter koji ce presretati SVAKI zahtev klijenta ka serveru 
// (sem nad putanjama navedenim u WebSecurityConfig.configure(WebSecurity web))
// Filter proverava da li JWT token postoji u Authorization header-u u zahtevu koji stize od klijenta
// Ukoliko token postoji, proverava se da li je validan. Ukoliko je sve u redu, postavlja se autentifikacija
// u SecurityContext holder kako bi podaci o korisniku bili dostupni u ostalim delovima aplikacije gde su neophodni
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private final TokenUtils tokenUtils;
	private final UserDetailsService userDetailsService;

	public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService) {
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String username;
		
		// 1. Preuzimanje JWT tokena i cookie iz zahteva
		String authToken = tokenUtils.getToken(request);
		String fingerprint = tokenUtils.getFingerprintFromCookie(request);

		try {
	
			if (authToken != null) {
				
				// 2. Citanje korisnickog imena iz tokena
				username = tokenUtils.getUsernameFromToken(authToken);
				
				if (username != null) {
					
					// 3. Preuzimanje korisnika na osnovu username-a
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);

					// 4. Provera da li je prosledjeni token validan
					if (tokenUtils.validateToken(authToken, userDetails, fingerprint)) {
						
						// 5. Kreiraj autentifikaciju
						TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
						authentication.setToken(authToken);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
			
		} catch (ExpiredJwtException ex) {
			log.debug("Token expired!");
		} 
		
		// prosledi request dalje u sledeci filter
		chain.doFilter(request, response);
	}

}