package com.backend.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.backend.admin.model.CertificateSigningRequest;
import com.backend.admin.service.CertificateSigningRequestService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/csr", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CertificateSigningRequestController {
    private final CertificateSigningRequestService certificateSigningRequestService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('READ_CSR')")
    public ResponseEntity<List<CertificateSigningRequest>> getAllCSR() {
        return new ResponseEntity<>(certificateSigningRequestService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CertificateSigningRequest> create(@RequestBody CertificateSigningRequest csr) {
        return new ResponseEntity<>(certificateSigningRequestService.create(csr), HttpStatus.OK);
    }

    @PostMapping("/generate-certificate")
    @PreAuthorize("hasAuthority('GENERATE_CER')")
    public ResponseEntity<List<CertificateSigningRequest>> generateCertificate(
            @RequestBody CertificateSigningRequest csr) throws Exception {
        return new ResponseEntity<>(certificateSigningRequestService.generateCertificate(csr), HttpStatus.OK);
    }

    @PutMapping("/verify/{id}")
    public ResponseEntity<Void> verifyCSR(@PathVariable String id) throws Exception {
        certificateSigningRequestService.verify(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
