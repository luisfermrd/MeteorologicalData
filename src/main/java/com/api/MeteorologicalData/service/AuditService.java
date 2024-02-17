package com.api.MeteorologicalData.service;

import com.api.MeteorologicalData.entity.Audit;
import com.api.MeteorologicalData.repository.AuditRepository;
import org.springframework.stereotype.Service;

/**
 * Service to manage operations related to the Audit entity.
 */
@Service
public class AuditService {

    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    /**
     * Saves an Audit entity using the audit repository.
     *
     * @param audit The Audit entity to save.
     */
    public void save(Audit audit) {
        this.auditRepository.save(audit);
    }
}
