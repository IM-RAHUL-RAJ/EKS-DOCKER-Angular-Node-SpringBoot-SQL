package com.capstone.services.v2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.capstone.dto.FmtsTokenResponse;
import com.capstone.exceptions.DatabaseException;
import com.capstone.exceptions.EmailAlreadyExistsException;
import com.capstone.exceptions.IdentificationAlreadyExistsException;
import com.capstone.exceptions.InvalidEmailException;
import com.capstone.integration.ClientDao;
import com.capstone.integration.FmtsDao;
import com.capstone.models.Client;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientDao clientDAO;

	@Autowired
	private FmtsDao fmtsDao;

	private Long token;

	@Override
	public boolean verifyEmail(String email) {
		if (!isValidEmail(email)) {
			throw new InvalidEmailException("Invalid email format.");
		}
		return clientDAO.isEmailUnique(email);
	}

	@Override
	public Client login(String email, String password) {
		Client client = clientDAO.findByEmail(email);
		if (client != null && client.getPassword().equals(password)) {
			return client;
		}
		throw new InvalidEmailException("Invalid email or password.");
	}

	@Override
	public void register(Client client) {
		if (!verifyEmail(client.getEmail())) {
			throw new EmailAlreadyExistsException("Email already exists.");
		}
		if (!clientDAO.isIdentificationUnique(client.getIdentificationType(), client.getIdentificationNumber())) {
			throw new IdentificationAlreadyExistsException("Identification already exists.");
		}
		try {
			FmtsTokenResponse response = fmtsDao.verifyClientToGetToken(client.getEmail());
			if(response != null) {
				this.token = response.getToken();
				System.out.print("TOKEN 222" +this.token);
				clientDAO.save(client);
			} else throw new EmailAlreadyExistsException("Email already exists.");
		} catch (DuplicateKeyException e) {
			throw new DuplicateKeyException("User already exists.");
		}
	}

	private boolean isValidEmail(String email) {
		System.out.println("EMAIL VALIDAITY CHECKER 77158 1" + email);
		String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		System.out.println("EMAIL VALIDAITY CHECKER 77158 1" + matcher.matches());
		return matcher.matches();
	}

	public Long getToken() {
		return this.token;
	}
}