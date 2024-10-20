package com.capstone.integration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.capstone.dto.EmailDTO;
import com.capstone.dto.FmtsTokenResponse;

@Repository
public class FmtsDaoImpl implements FmtsDao {
	private final RestTemplate restTemplate;

	@Value("${fmt.verify.url:http://localhost:3000/fmts/client}")
	private String fmtVerifyUrl;

	public FmtsDaoImpl(RestTemplate restTemplate) {
	    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(converter);
		this.restTemplate = restTemplate;
        this.restTemplate.setMessageConverters(messageConverters);
	}

	@Override
	public FmtsTokenResponse verifyClientToGetToken(String email) {
	  EmailDTO request = new EmailDTO(email);

	  HttpHeaders headers = new HttpHeaders();
	  headers.setContentType(MediaType.APPLICATION_JSON);

	  HttpEntity<EmailDTO> entity = new HttpEntity<>(request, headers);

	  ResponseEntity<FmtsTokenResponse> response = restTemplate.exchange(
	      fmtVerifyUrl, HttpMethod.POST, entity, FmtsTokenResponse.class);
	  if (response.getStatusCode() == HttpStatus.OK) {
	    return response.getBody();
	  } else {
	    throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
	  }
	}
}
