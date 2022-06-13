package com.backend.admin.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.admin.model.cert.CertificateSigningRequest;
import com.backend.admin.repository.CertificateSigningRequestRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CertificateSigningRequestService {
    private final CertificateSigningRequestRepository certificateSigningServiceRepository;
    private final CertificateService certificateService;
    private final EmailService emailService;

    public CertificateSigningRequest create(CertificateSigningRequest csr) {
        csr = certificateSigningServiceRepository.save(csr);
        emailService.sendMailWithHTML(csr.getEmail(),
                "Certificate Signing Request verification", getVerificationMailText(csr));
        return csr;
    }

    public List<CertificateSigningRequest> getAll() {
        return certificateSigningServiceRepository.getAllByVerifiedTrue();
    }

    public List<CertificateSigningRequest> generateCertificate(CertificateSigningRequest csr) throws Exception {
        if (!csr.getVerified()) throw new Exception("CSR not verified");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 5);
        csr.setEndDate(c.getTime());
        certificateService.generateCertificate(csr);
        certificateSigningServiceRepository.deleteById(csr.getId());
        return getAll();
    }

    public void verify(String id) throws Exception {
        CertificateSigningRequest csr = certificateSigningServiceRepository.findById(id)
                .orElseThrow(() -> new Exception("CSR not found"));

        csr.setVerified(true);
        certificateSigningServiceRepository.save(csr);
    }

    private String getVerificationMailText(CertificateSigningRequest csr) {
        return "<html>" +
                "<ul>" +
                "<li>Common name: <b>" + csr.getCommonName() + "<b></li>" +
                "<li>Name: <b>" + csr.getFirstName() +" "+ csr.getLastName() + "<b></li>" +
                "<li>e-mail: <b>" + csr.getEmail() + "<b></li>" +
                "<li>City, state, country: <b>" + csr.getCity() +", "+ csr.getState() +", "+ csr.getCountry() + "<b></li>" +
                "<li>Organization, org. unit: <b>" + csr.getOrganization() +", "+ csr.getOrganizationalUnit() + "</li>" +
                "</ul>" +
                "<a href=\"http://localhost:3000/verify-csr/" + csr.getId() + "\">VERIFY</a>" +
                "</html>";
    }

}
