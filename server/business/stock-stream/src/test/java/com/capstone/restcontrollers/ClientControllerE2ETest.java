package com.capstone.restcontrollers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import com.capstone.dto.EmailDTO;
import com.capstone.dto.LivePricingResponse;
import com.capstone.models.Client;
import com.capstone.models.Holding;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:init.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientControllerE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DELETE FROM Holdings");
        
    }

    
    @Test
    public void testGetHoldings() {
        String requestUrl = "/api/clients/holdings/C001";

//        ResponseEntity<List<Holding>> response = restTemplate.exchange(
//            requestUrl,
//            HttpMethod.GET,
//            null,
//            new ParameterizedTypeReference<List<Holding>>() {}
//        );
        
        ResponseEntity<Holding[]> response = restTemplate.getForEntity(requestUrl, Holding[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Holding> holdings = List.of(response.getBody());
        assertEquals(2, holdings.size());

        assertEquals("Stock A", holdings.get(0).getInstrument());
        assertEquals("INST001", holdings.get(0).getInstrumentId());
        assertEquals(100, holdings.get(0).getQuantity());
        assertEquals(new BigDecimal("150.5"), holdings.get(0).getAveragePrice());
        assertEquals(15050.0, holdings.get(0).getInvestedCapital(), 0.01);
        assertEquals(new BigDecimal("155"), holdings.get(0).getLtp());
        assertEquals(3.0, holdings.get(0).getPercentChange(), 0.01);
        assertEquals(450.0, holdings.get(0).getProfitLoss(), 0.01);
        assertEquals(2.5, holdings.get(0).getDayChangePercent(), 0.01);
        assertEquals("C001", holdings.get(0).getClientId());

        assertEquals("Stock B", holdings.get(1).getInstrument());
        assertEquals("INST002", holdings.get(1).getInstrumentId());
        assertEquals(50, holdings.get(1).getQuantity());
        assertEquals(new BigDecimal("200"), holdings.get(1).getAveragePrice());
        assertEquals(10000.0, holdings.get(1).getInvestedCapital(), 0.01);
        assertEquals(new BigDecimal("195"), holdings.get(1).getLtp());
        assertEquals(-2.5, holdings.get(1).getPercentChange(), 0.01);
        assertEquals(-250.0, holdings.get(1).getProfitLoss(), 0.01);
        assertEquals(-1.25, holdings.get(1).getDayChangePercent(), 0.01);
        assertEquals("C001", holdings.get(1).getClientId());
    }

    
}