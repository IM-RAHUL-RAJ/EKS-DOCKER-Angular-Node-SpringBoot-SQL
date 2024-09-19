package com.fidelity.capstone.stock_stream;

public class Client {
	private String email;
    private String password;
    private String fullName;
    private String dateOfBirth;
    private String country;
    private String postalCode;
    private String identificationValue;
    private String clientId; 
    
	public Client(String email, String password, String fullName, String dateOfBirth, String country, String postalCode,
			String identificationValue, String clientId) {
		super();
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.country = country;
		this.postalCode = postalCode;
		this.identificationValue = identificationValue;
		this.clientId = clientId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getIdentificationValue() {
		return identificationValue;
	}

	public void setIdentificationValue(String identificationValue) {
		this.identificationValue = identificationValue;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	    
}