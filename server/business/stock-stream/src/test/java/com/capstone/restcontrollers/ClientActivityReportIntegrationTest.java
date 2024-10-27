package com.capstone.restcontrollers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;
import static org.springframework.test.jdbc.JdbcTestUtils.deleteFromTables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import com.capstone.models.ClientActivityReport;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:clientActivityReport.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientActivityReportIntegrationTest {

	@Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DELETE FROM ClientActivityReport");
        jdbcTemplate.execute("INSERT INTO ClientActivityReport (id, title, summary) VALUES (1, 'Monthly Performance Overview for C001', 'A detailed summary of the client''s portfolio performance over the past month, including key metrics such as total returns, top-performing assets, and areas for improvement.')");
        jdbcTemplate.execute("INSERT INTO ClientActivityReport (id, title, summary) VALUES (2, 'Quarterly Investment Insights for C001', 'An in-depth analysis of the client''s investment activities for the quarter, highlighting significant market trends, portfolio adjustments, and strategic recommendations for the next quarter.')");
        jdbcTemplate.execute("INSERT INTO ClientActivityReport (id, title, summary) VALUES (3, 'Annual Financial Health Check for C001', 'A comprehensive review of the client''s financial health over the past year, covering portfolio performance, risk assessment, and personalized advice for achieving long-term financial goals.')");
        jdbcTemplate.execute("INSERT INTO ClientActivityReport (id, title, summary) VALUES (4, 'Customized Market Analysis Report for C001', 'A tailored report providing insights into market trends and forecasts relevant to the client''s investment strategy, including sector performance, economic indicators, and potential opportunities.')");
    }

    @Test
    public void testQueryForAllClientActivityReports() {
        String requestUrl = "http://localhost:8080/api/clients/reports/C001";

        ResponseEntity<ClientActivityReport[]> response = restTemplate.getForEntity(requestUrl, ClientActivityReport[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ClientActivityReport[] reports = response.getBody();
        assertEquals(4, reports.length);
        assertEquals("Annual Financial Health Check for C001", reports[0].getTitle());
        
    }

    @Test
    public void testQueryForAllClientActivityReports_NoReportsInDb() {
        jdbcTemplate.execute("DELETE FROM ClientActivityReport");

        String requestUrl = "http://localhost:8080/api/clients/reports/C001";

        ResponseEntity<ClientActivityReport[]> response = restTemplate.getForEntity(requestUrl, ClientActivityReport[].class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    
}