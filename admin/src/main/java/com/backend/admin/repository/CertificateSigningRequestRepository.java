package com.backend.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.backend.admin.model.CertificateSigningRequest;

import java.util.List;

@Repository
public interface CertificateSigningRequestRepository extends MongoRepository<CertificateSigningRequest, String> {
    List<CertificateSigningRequest> getAllByVerifiedTrue();
}
