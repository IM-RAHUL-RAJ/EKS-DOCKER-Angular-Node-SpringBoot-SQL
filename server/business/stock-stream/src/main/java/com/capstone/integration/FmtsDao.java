package com.capstone.integration;

import java.util.List;

import com.capstone.dto.FmtsTokenResponse;
import com.capstone.models.Price;

public interface FmtsDao {
	FmtsTokenResponse verifyClientToGetToken(String email);
	List<Price> getInstrumentsByCategory(String category);
}
