package com.api.MeteorologicalData.service;

import com.api.MeteorologicalData.entity.Audit;
import com.api.MeteorologicalData.repository.AuditRepository;
import com.api.MeteorologicalData.security.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

import static org.mockito.Mockito.*;

@SpringBootTest
class AuditServiceTest {

    @MockBean
    private AuditRepository auditRepository;
    @Autowired
    private AuditService auditService;
    private Audit audit;

    @BeforeEach
    void setUp() {
        this.audit = new Audit();
        this.audit.setUser(new User());
        this.audit.setRequests("Request");
        this.audit.setDateRequets(new Date());
        this.audit.setResponts("Responts");
        when(this.auditRepository.save(this.audit)).thenReturn(this.audit);
    }

    /**
     * Tests the save method of the AuditService class.
     */
    @Test
    void save() {
        this.auditService.save(this.audit);
        verify(auditRepository, times(1)).save(this.audit);
    }
}