package com.capstone.services;

import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class FmtsService {
    public boolean verifyClient(String identificationValue, String country) {

    	return true;
    }
}
