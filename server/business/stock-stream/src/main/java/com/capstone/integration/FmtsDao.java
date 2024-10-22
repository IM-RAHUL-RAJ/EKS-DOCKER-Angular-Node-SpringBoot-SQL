package com.capstone.integration;

import com.capstone.dto.FmtsTokenResponse;

public interface FmtsDao {
	FmtsTokenResponse verifyClientToGetToken(String email);
}
