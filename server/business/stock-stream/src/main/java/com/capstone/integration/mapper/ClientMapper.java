package com.capstone.integration.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.capstone.models.Client;

@Mapper
public interface ClientMapper {
	int isEmailUnique(@Param("email") String email);

	int isIdentificationUnique(@Param("identificationType") String identificationType,
			@Param("identificationNumber") String identificationNumber);

	Client findByEmail(@Param("email") String email);

	void save(Client client);
}
