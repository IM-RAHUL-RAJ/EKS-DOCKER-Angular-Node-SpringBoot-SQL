package com.capstone.restcontroller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.capstone.dto.LivePricingResponse;
import com.capstone.integration.ClientHoldingDao;
import com.capstone.integration.FmtsDao;
import com.capstone.models.Holding;
import com.capstone.services.v2.HoldingService;


@RestController
@RequestMapping("/client")
public class PortfolioController {

    @Autowired
    private Logger logger;

    @Autowired
    private ClientHoldingDao dao;

    @Autowired
    private HoldingService service;

    @Autowired
    private FmtsDao fmtsDao;
    
    @GetMapping(value="/ping", produces=MediaType.ALL_VALUE)
    public String ping() {
        return "Client Holdings is alive at " + LocalDateTime.now();
    }

    @GetMapping("/holdings/{id}")
    public ResponseEntity<List<Holding>> getWidgets(@PathVariable String id) {
        try {
            List<Holding> holdings = service.getClientPortfolio(id);
            if (holdings != null && !holdings.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(holdings);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            logger.error("problem getting widgets", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve books", e);
        }
    }
    
    @GetMapping("/live-prices")
    public ResponseEntity<List<LivePricingResponse>> getLivePrices() {
        try {
            List<LivePricingResponse> livePricing = fmtsDao.getLivePricing();
            return ResponseEntity.status(HttpStatus.OK).body(livePricing);
        } catch (Exception e) {
            logger.error("problem getting live prices", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve live prices", e);
        }
    }

   
}
