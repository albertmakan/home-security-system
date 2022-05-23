package com.backend.admin.controller.auth;

import com.backend.admin.dto.auth.JwtAuthenticationRequest;
import com.backend.admin.dto.auth.UserRequest;
import com.backend.admin.dto.auth.UserTokenState;
import com.backend.admin.exception.BadRequestException;
import com.backend.admin.exception.BlockedUserException;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

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

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        User user = userService.findByUsername(authenticationRequest.getUsername()).orElse(null);
        if (user == null)
            return ResponseEntity.badRequest().body(new UserTokenState("Authentication failed!", 0));

        if (user.isBlocked()) {
            if (user.getLastLoginAttemptDate().after(new Date(new Date().getTime()-900000)))
                return ResponseEntity.badRequest().body(new UserTokenState("User is blocked! Can't login!", 0));
            else user.setBlocked(false);
        }

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        }
        catch (AuthenticationException ex) {
            user.setLoginAttempts(user.getLoginAttempts() + 1);
            user.setLastLoginAttemptDate(new Date());
            if (user.getLoginAttempts() >= 3) {
                System.err.println("Blocking user: " + user.getUsername());
                user.setBlocked(true);
            }
            userService.save(user);
            return ResponseEntity.badRequest().body(new UserTokenState("Authentication failed!", 0));
        }
        user.setLoginAttempts(0);
        userService.save(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String fingerprint = tokenUtils.generateFingerprint();
        String jwt = tokenUtils.generateToken(user.getUsername(), fingerprint, user);
        int expiresIn = tokenUtils.getExpiredIn();

        // Kreiraj cookie
        // String cookie = "__Secure-Fgp=" + fingerprint + "; SameSite=Strict; HttpOnly; Path=/; Secure";  
        // kasnije mozete probati da postavite i ostale atribute, ali tek nakon sto podesite https
        String cookie = "Fingerprint=" + fingerprint + "; HttpOnly; Path=/";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie);

        return ResponseEntity.ok().headers(headers).body(new UserTokenState(jwt, expiresIn));
    }

    @PostMapping("/revokeJWT")
    @PreAuthorize("hasAuthority('REVOKE_TOKEN')")
    public ResponseEntity<Void> revokeJWT(HttpServletRequest request){
        String token = tokenUtils.getToken(request);
        System.out.println("Revoking token: " + token);
        RevokedToken revoked = new RevokedToken();
        revoked.setToken(token);
        revoked.setDate(new Date());
        revokedTokensRepository.save(revoked);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/whoami")
    public ResponseEntity<UserDetails> whoami(@AuthenticationPrincipal UserDetails user) {

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // remove later this method from auth controller
    @PostMapping("/register")
    public ResponseEntity<User> addUser(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.create(userRequest), HttpStatus.CREATED);
    }
}