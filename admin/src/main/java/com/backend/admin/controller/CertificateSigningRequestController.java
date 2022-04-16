package com.backend.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.backend.admin.model.CertificateSigningRequest;
import com.backend.admin.service.CertificateSigningRequestService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/csr", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CertificateSigningRequestController {
    private final CertificateSigningRequestService certificateSigningRequestService;

    @GetMapping("/all")
    public ResponseEntity<List<CertificateSigningRequest>> getAllCSR() {
        return new ResponseEntity<>(certificateSigningRequestService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CertificateSigningRequest> create(@RequestBody CertificateSigningRequest csr) {
        return new ResponseEntity<>(certificateSigningRequestService.create(csr), HttpStatus.OK);
    }

}
