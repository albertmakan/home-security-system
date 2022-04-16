package com.backend.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.admin.model.CertificateSigningRequest;
import com.backend.admin.repository.CertificateSigningRequestRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CertificateSigningRequestService {
    private final CertificateSigningRequestRepository certificateSigningServiceRepository;

    public CertificateSigningRequest create(CertificateSigningRequest csr) {
        return certificateSigningServiceRepository.save(csr);
    }

    public List<CertificateSigningRequest> getAll() {
        return certificateSigningServiceRepository.findAll();
    }

}
