package com.capstone.integration;

import java.util.List;

import com.capstone.dto.FmtsTokenResponse;
import com.capstone.dto.LivePricingResponse;

public interface FmtsDao {
	FmtsTokenResponse verifyClientToGetToken(String email);
    List<LivePricingResponse> getLivePricing();

}
