package com.backend.admin.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDService {
    public String getUUID() {
        return String.valueOf(UUID.randomUUID().getLeastSignificantBits() * -1);
    }
}