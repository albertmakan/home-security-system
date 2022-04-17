package com.backend.admin.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.admin.model.CertificateSigningRequest;
import com.backend.admin.repository.CertificateSigningRequestRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CertificateSigningRequestService {
    private final CertificateSigningRequestRepository certificateSigningServiceRepository;
    private final CertificateService certificateService;

    public CertificateSigningRequest create(CertificateSigningRequest csr) {
        return certificateSigningServiceRepository.save(csr);
    }

    public List<CertificateSigningRequest> getAll() {
        return certificateSigningServiceRepository.findAll();
    }

    public List<CertificateSigningRequest> generateCertificate(CertificateSigningRequest csr) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 5);
        csr.setEndDate(c.getTime());
        certificateService.generateCertificate(csr);
        certificateSigningServiceRepository.deleteById(csr.getId());
        return getAll();
    }

}
