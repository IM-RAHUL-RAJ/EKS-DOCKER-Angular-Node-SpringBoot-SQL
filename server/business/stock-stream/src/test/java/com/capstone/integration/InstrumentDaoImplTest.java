package com.capstone.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.models.Price;

@SpringBootTest
@Transactional
class InstrumentDaoImplTest {

	@Autowired
	InstrumentDao instrumentDao;
	
	@Test
	void getAllInstrumentsToSucceed() {
		List<Price> instruments = instrumentDao.getAllInstruments();
		System.out.println(instruments);
		assertEquals(instruments.size(), 13);
	}

}
