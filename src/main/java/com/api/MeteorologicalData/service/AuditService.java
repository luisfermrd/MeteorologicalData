package com.api.MeteorologicalData.service;

import com.api.MeteorologicalData.entity.Audit;
import com.api.MeteorologicalData.repository.AuditRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void save(Audit audit) {
        this.auditRepository.save(audit);
    }
}
