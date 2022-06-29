package com.backend.myhouse.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.backend.myhouse.security.MaliciousRequestCheckFilter.MaliciousRequestCheckFilter;
import com.backend.myhouse.security.auth.RestAuthenticationEntryPoint;
import com.backend.myhouse.security.auth.TokenAuthenticationFilter;
import com.backend.myhouse.services.UserService;
import com.backend.myhouse.util.TokenUtils;

import lombok.AllArgsConstructor;


@Configuration
@AllArgsConstructor
@Import(KieConfig.class)
// Ukljucivanje podrske za anotacije "@Pre*" i "@Post*" koje ce aktivirati autorizacione provere za svaki pristup metodi
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Servis koji se koristi za citanje podataka o korisnicima aplikacije
	private UserService customUserDetailsService;
	// Handler za vracanje 401 kada klijent sa neodogovarajucim korisnickim imenom i lozinkom pokusa da pristupi resursu
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	// Injektujemo implementaciju iz TokenUtils klase kako bismo mogli da koristimo njene metode za rad sa JWT u TokenAuthenticationFilteru
	private TokenUtils tokenUtils;

    private MaliciousRequestCheckFilter maliciousRequestCheckFilter;

    private KieConfig kieConfig;

    @PostConstruct
	public void init() throws Exception {
        maliciousRequestCheckFilter.setRulesSession(kieConfig.rulesSession());
        maliciousRequestCheckFilter.setEventsSession(kieConfig.eventsSession());

	}

	// Definisemo prava pristupa za zahteve ka odredjenim URL-ovima/rutama
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// komunikacija izmedju klijenta i servera je stateless posto je u pitanju REST aplikacija
			// ovo znaci da server ne pamti nikakvo stanje, tokeni se ne cuvaju na serveru 
			// ovo nije slucaj kao sa sesijama koje se cuvaju na serverskoj strani - STATEFULL aplikacija
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

			// sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
			.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

			// svim korisnicima dopusti da pristupe sledecim putanjama:
			.authorizeRequests().antMatchers("/api/**").permitAll()		// /auth/**
								.antMatchers("/h2-console/**").permitAll()	// /h2-console/** ako se koristi H2 baza)
								.antMatchers("/api/foo").permitAll()		// /api/foo
								
			// ukoliko ne zelimo da koristimo @PreAuthorize anotacije nad metodama kontrolera, moze se iskoristiti hasRole() metoda da se ogranici
			// koji tip korisnika moze da pristupi odgovarajucoj ruti. Npr. ukoliko zelimo da definisemo da ruti 'admin' moze da pristupi
			// samo korisnik koji ima rolu 'ADMIN', navodimo na sledeci nacin: 
			// .antMatchers("/admin").hasRole("ADMIN") ili .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
							       
			// za svaki drugi zahtev korisnik mora biti autentifikovan
			.anyRequest().authenticated().and()
			
			// za development svrhe ukljuci konfiguraciju za CORS iz WebConfig klase
			.cors().and()
            
            //CUSTOM REQUEST FILTER -> MALICIOUS IPS AND TOO MANY REQUESTS CHECKS
            .addFilterBefore(maliciousRequestCheckFilter, BasicAuthenticationFilter.class)

			// umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT tokena umesto cistih korisnickog imena i lozinke (koje radi BasicAuthenticationFilter)
			.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, customUserDetailsService), BasicAuthenticationFilter.class);
		
		// zbog jednostavnosti primera ne koristimo Anti-CSRF token (https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html)
		http.csrf().disable();

		// Aktiviramo ugrađenu zaštitu od XSS napada da browser ne bi izvršavao maliciozne skripte
		http
			.headers()
			.xssProtection()
			.and()
			.contentSecurityPolicy("script-src 'self'");
	}

	// Definisanje konfiguracije koja utice na generalnu bezbednost aplikacije
	@Override
	public void configure(WebSecurity web) {
		// Autentifikacija ce biti ignorisana ispod navedenih putanja (kako bismo ubrzali pristup resursima)
		// Zahtevi koji se mecuju za web.ignoring().antMatchers() nemaju pristup SecurityContext-u
		
		// Dozvoljena POST metoda na ruti /auth/login, za svaki drugi tip HTTP metode greska je 401 Unauthorized
		 web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/login");
		 
		// Ovim smo dozvolili pristup statickim resursima aplikacije
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js");
	}

}
