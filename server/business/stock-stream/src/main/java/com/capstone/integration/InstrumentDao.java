package com.capstone.integration;

import java.util.List;

import com.capstone.models.Price;

public interface InstrumentDao {

	List<Price> getAllInstruments();

}