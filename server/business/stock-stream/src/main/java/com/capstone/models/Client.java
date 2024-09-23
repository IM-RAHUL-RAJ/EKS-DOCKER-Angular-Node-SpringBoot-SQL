package com.capstone.models;

public class Client {
	private String email;
    private String password;
    private String fullName;
    private String dateOfBirth;
    private String country;
    private String postalCode;
    private String identificationType;
    private String identificationNumber;
    private String clientId; 
    
    public Client(String email, String password, String fullName, String dateOfBirth, String country, String postalCode,
			String identificationType, String identificationNumber, String clientId) {
		super();
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.country = country;
		this.postalCode = postalCode;
		this.identificationType = identificationType;
		this.identificationNumber = identificationNumber;
		this.clientId = clientId;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFullName() {
		return fullName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getCountry() {
		return country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public String getClientId() {
		return clientId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}  

	
	    
}