package com.backend.admin.support;

import java.security.cert.X509Certificate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.backend.admin.dto.CertificateDTO;
import com.backend.admin.model.CertificateInfo;
import com.backend.admin.service.CertificateKeyStoreService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CertificateInfoToCertificateDTO implements Converter<CertificateInfo, CertificateDTO> {
    private final CertificateKeyStoreService certificateKeyStoreService;

    @Override
    public CertificateDTO convert(CertificateInfo source) {
        X509Certificate certificate = certificateKeyStoreService.readCertificate(source.getAlias());
        return new CertificateDTO(certificate.getIssuerDN().getName(), certificate.getSubjectDN().getName(),
                certificate.getNotAfter(), source.getSerialNumber(), source.getAlias(), source.getSubjectEmail(),
                source.getType());
    }

    public List<CertificateDTO> convert(List<CertificateInfo> infoList) {
        return infoList.stream().map(this::convert).collect(Collectors.toList());
    }

}
