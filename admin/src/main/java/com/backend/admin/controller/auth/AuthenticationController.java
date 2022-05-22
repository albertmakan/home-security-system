package com.backend.admin.controller.auth;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.backend.admin.dto.auth.JwtAuthenticationRequest;
import com.backend.admin.dto.auth.UserRequest;
import com.backend.admin.dto.auth.UserTokenState;
import com.backend.admin.exception.BadRequestException;
import com.backend.admin.exception.BlockedUserException;
import com.backend.admin.exception.ResourceConflictException;
import com.backend.admin.model.auth.RevokedToken;
import com.backend.admin.model.auth.User;
import com.backend.admin.repository.auth.RevokedTokensRepository;
import com.backend.admin.service.auth.UserService;
import com.backend.admin.util.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    
    @Autowired
    private RevokedTokensRepository revokedTokensRepository;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException

        Optional<User> optionalUser = userService.findByUsername(authenticationRequest.getUsername());

        //checking if user is blocked first
        if (optionalUser.isPresent()){
            if (optionalUser.get().isBlocked()){
                throw new BlockedUserException("User is blocked! Can't login!");
            } 
        }

        Authentication authentication = null;

        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        }
        
        catch (AuthenticationException ex){
            System.out.println("auth failed");

            //failed login
            if (optionalUser.isPresent()){
                //if user exists, count failed logins
                User u = optionalUser.get();
                u.setLoginAttempts(u.getLoginAttempts() + 1);

                //blocking user if more than 3 failed logins
                if (u.getLoginAttempts() >= 3){
                    System.err.println("Blocking user: " + u.getUsername());
                    u.setBlocked(true);
                }

                userService.save(u);

            }
            throw new BadRequestException("Authentication failed!");

        }
        

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        System.out.println(authentication.getName());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        System.out.println("loggin in: " + user.getUsername());
        String fingerprint = tokenUtils.generateFingerprint();
        String jwt = tokenUtils.generateToken(user.getUsername(), fingerprint);
        int expiresIn = tokenUtils.getExpiredIn();

        // Kreiraj cookie
        // String cookie = "__Secure-Fgp=" + fingerprint + "; SameSite=Strict; HttpOnly; Path=/; Secure";  
        // kasnije mozete probati da postavite i ostale atribute, ali tek nakon sto podesite https
        String cookie = "Fingerprint=" + fingerprint + "; HttpOnly; Path=/";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie);

        // login is successful -> resetting login attempts counter
        User u = optionalUser.get();
        u.setLoginAttempts(0);
        userService.save(u);

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok().headers(headers).body(new UserTokenState(jwt, expiresIn));
    }

    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
    public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucBuilder) throws Exception {

        Optional<User> optionalUser = this.userService.findByUsername(userRequest.getUsername());

        if (optionalUser.isPresent()) {
            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
        }

        User user = this.userService.save(userRequest);

        return new ResponseEntity<>(user, HttpStatus.CREATED);


    }

    @PostMapping("/revokeJWT")
    public ResponseEntity<Void> revokeJWT(@RequestHeader HttpHeaders headers){

        String token = headers.getFirst(HttpHeaders.AUTHORIZATION).split(" ")[1];
        System.out.println("Revoking token: " + token);
        RevokedToken revoked = new RevokedToken();
        revoked.setToken(token);
        revoked.setDate(new Date());
        revokedTokensRepository.save(revoked);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/whoami")
    public ResponseEntity<UserDetails> whoami(@AuthenticationPrincipal UserDetails user) {

        System.out.println(user);
        return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
    }



}