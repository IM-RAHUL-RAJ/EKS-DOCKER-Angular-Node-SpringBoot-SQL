package com.capstone.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.capstone.models.Price;

@Repository
public class InstrumentDaoImpl implements InstrumentDao {
	
	private String instrumentsUrl = "http://127.0.0.1:3000/fmts/trades/prices";
	
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public List<Price> getAllInstruments(){
		ResponseEntity<Price[]> responseEntity = restTemplate.getForEntity(instrumentsUrl, Price[].class);
		
		List<Price> instruments = List.of(responseEntity.getBody());
		
		return instruments;
	}

}
