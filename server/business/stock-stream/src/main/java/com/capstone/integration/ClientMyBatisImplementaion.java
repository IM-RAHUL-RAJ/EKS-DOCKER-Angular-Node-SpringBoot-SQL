package com.capstone.integration;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.capstone.integration.mapper.ClientMapper;
import com.capstone.models.Client;

@Repository("clientDao")
@Primary
public class ClientMyBatisImplementaion implements ClientDao {
	
	@Autowired
	ClientMapper clientMapper;

	@Override
	public boolean verifyEmailAddress(String emailAddress) {
		 if (emailAddress == null || emailAddress.isEmpty()) {
	            throw new IllegalArgumentException("Email cannot be null or empty");
	        }

	        int count = clientMapper.verifyEmailAddress(emailAddress);
	        
	        // Return true if count > 0, otherwise false
	        return count > 0;
	    }
	

	@Override
	public void addClient(Client client) throws SQLException {
		 if (client.getEmail() == null || client.getPassword() == null || client.getFullName() == null) {
	            throw new IllegalArgumentException("Cannot add user: Required fields are missing");
	        }
		 clientMapper.addClient(client);
	}

	@Override
	public boolean verifyLogin(String email, String password) {
		
		if ( password == null ||  password.isEmpty()) {
			throw new IllegalArgumentException(" password cannot be null or empty");
		}
		int count = clientMapper.verifyLogin(email, password);
		return count>0;
	}

}
