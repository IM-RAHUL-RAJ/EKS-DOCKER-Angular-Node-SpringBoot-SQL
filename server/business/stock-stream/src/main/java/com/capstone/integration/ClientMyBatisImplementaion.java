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
		// TODO Auto-generated method stub
		 if (emailAddress == null || emailAddress.isEmpty()) {
	            throw new IllegalArgumentException("Email cannot be null or empty");
	        }

	        // Call the mapper method
	        int count = clientMapper.verifyEmailAddress(emailAddress);
	        
	        // Return true if count > 0, otherwise false
	        return count > 0;
	    }
	

	@Override
	public void addClient(Client client) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean verifyLogin(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
