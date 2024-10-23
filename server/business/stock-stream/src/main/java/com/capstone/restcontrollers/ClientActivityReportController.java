package com.capstone.restcontrollers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.integration.ClientActivityReportDao;
import com.capstone.models.ClientActivityReport;
import com.capstone.services.ClientActivityReportService;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientActivityReportController {

    @Autowired
    private Logger logger;

    @Autowired
    private ClientActivityReportDao dao;

    @Autowired
    private ClientActivityReportService service;

    @GetMapping(value = "/reports/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClientActivityReport>> getClientActivityReports(@PathVariable String clientId) {
        logger.info("Fetching client activity reports for client ID: " + clientId);
        try {
            List<ClientActivityReport> reports = service.getClientActivityReports(clientId);
            logger.info("Reports fetched: " + reports);
            if (reports.isEmpty()) {
                logger.info("No content for client ID: " + clientId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(reports, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid client ID: " + clientId, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error fetching client activity reports for client ID: " + clientId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
