package com.backend.admin.repository;

import com.backend.admin.model.cert.CertificateInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificateInfoRepository extends MongoRepository<CertificateInfo, ObjectId> {
    Optional<CertificateInfo> findBySerialNumber(String serialNumber);
}
