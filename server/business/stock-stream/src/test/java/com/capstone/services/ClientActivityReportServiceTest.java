package com.fidelity.capstone.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fidelity.capstone.stock_stream.ClientActivityReport;

public class ClientActivityReportServiceTest {

    private ClientActivityReportService service;

    @BeforeEach
    public void setUp() {
        service = new ClientActivityReportService();
    }

    @Test
	@DisplayName("If the client id is null exception must be thrown")
    public void testGetClientActivityReports_NullClientId() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getClientActivityReports(null);
        });
    }

    @Test
	@DisplayName("If the client id is empty then exception must be thrown")
    public void testGetClientActivityReports_EmptyClientId() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getClientActivityReports("");
        });
    }

    @Test
	@DisplayName("If client id is valid then the reports must be displayed")
    public void testGetClientActivityReports_ValidClientId() {
        List<ClientActivityReport> reports = service.getClientActivityReports("client123");
        assertNotNull(reports);
        assertEquals(3, reports.size());

        assertEquals("Annual Report 2023", reports.get(0).getTitle());
        assertEquals("Summary of the annual performance for 2023.", reports.get(0).getSummary());

        assertEquals("Quarterly Report Q1 2024", reports.get(1).getTitle());
        assertEquals("Summary of the first quarter performance for 2024.", reports.get(1).getSummary());

        assertEquals("Monthly Report August 2024", reports.get(2).getTitle());
        assertEquals("Summary of the performance for August 2024.", reports.get(2).getSummary());
    }
}
