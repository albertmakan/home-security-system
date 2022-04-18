package com.backend.admin.controller;

import com.backend.admin.dto.CertificateDTO;

import com.backend.admin.dto.RevokeCertificateDTO;
import com.backend.admin.model.CertificateInfo;
import com.backend.admin.model.enums.CertificateStatus;
import com.backend.admin.service.CertificateService;
import com.backend.admin.support.CertificateInfoToCertificateDTO;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.cert.X509Certificate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;
    private final CertificateInfoToCertificateDTO toDTO;

    @GetMapping("/all")
    public ResponseEntity<List<CertificateDTO>> getCertificateInfos() {
        return new ResponseEntity<>(toDTO.convert(certificateService.getCertificateInfos()), HttpStatus.OK);
    }

    @GetMapping("/{serialNo}")
    public ResponseEntity<CertificateDTO> getCertificate(@PathVariable String serialNo) {
        X509Certificate certificate;
        try {
            certificate = certificateService.findBySerialNumber(serialNo);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CertificateDTO certificateDTO = new CertificateDTO(certificate.getIssuerX500Principal().getName(),
                certificate.getSubjectX500Principal().getName(), certificate.getNotAfter());
        return new ResponseEntity<>(certificateDTO, HttpStatus.OK);
    }

    @PostMapping("/revoke")
    public ResponseEntity<List<CertificateDTO>> revoke(@RequestBody RevokeCertificateDTO revokeCertificateDTO) {
        try {
            certificateService.revokeCertificate(revokeCertificateDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(toDTO.convert(certificateService.getCertificateInfos()), HttpStatus.OK);
    }

    @GetMapping("/status/{serialNo}")
    public ResponseEntity<CertificateStatus> checkStatus(@PathVariable String serialNo) {
        CertificateStatus status;
        try {
            status = certificateService.checkStatus(serialNo);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
