package com.capstone.models;

import java.util.Objects;

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
	private ProfileStatus profileStatus;

	public Client(String email, String password, String fullName, String dateOfBirth, String country, String postalCode,
			String identificationType, String identificationNumber, ProfileStatus profileStatus, String clientId) {
		super();
		setEmail(email);
		setPassword(password);
		setFullName(fullName);
		setDateOfBirth(dateOfBirth);
		setCountry(country);
		setPostalCode(postalCode);
		setIdentificationType(identificationType);
		setIdentificationNumber(identificationNumber);
		setClientId(clientId);
		setProfileStatus(profileStatus);
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
//		Objects.requireNonNull(email, "Email cannot be null");
//		if (email.isEmpty()) {
//			throw new IllegalArgumentException("Email cannot be empty");
//		}
		this.email = email;
	}

	public void setPassword(String password) {
		Objects.requireNonNull(password, "password cannot be null");
		if (password.isEmpty()) {
			throw new IllegalArgumentException("password cannot be empty");
		}
		this.password = password;
	}

	public void setFullName(String fullName) {
		Objects.requireNonNull(fullName, "fullName cannot be null");
		if (fullName.isEmpty()) {
			throw new IllegalArgumentException("fullName cannot be empty");
		}
		this.fullName = fullName;
	}

	public void setDateOfBirth(String dateOfBirth) {
		Objects.requireNonNull(dateOfBirth, "dateOfBirth cannot be null");
		if (dateOfBirth.isEmpty()) {
			throw new IllegalArgumentException("dateOfBirth cannot be empty");
		}
		this.dateOfBirth = dateOfBirth;
	}

	public void setCountry(String country) {
		Objects.requireNonNull(country, "Email cannot be null");
		if (country.isEmpty()) {
			throw new IllegalArgumentException("country cannot be empty");
		}
		this.country = country;
	}

	public void setPostalCode(String postalCode) {
		Objects.requireNonNull(postalCode, "postalCode cannot be null");
        if (postalCode.isEmpty()) {
            throw new IllegalArgumentException("postalCode cannot be empty");
        }
		this.postalCode = postalCode;
	}

	public void setIdentificationType(String identificationType) {
		Objects.requireNonNull(identificationType, "identificationType cannot be null");
        if (identificationType.isEmpty()) {
            throw new IllegalArgumentException("identificationType cannot be empty");
        }
		this.identificationType = identificationType;
	}

	public void setIdentificationNumber(String identificationNumber) {
		Objects.requireNonNull(identificationNumber, "Email cannot be null");
        if (identificationNumber.isEmpty()) {
            throw new IllegalArgumentException("identificationNumber cannot be empty");
        }
		this.identificationNumber = identificationNumber;
	}

	public void setClientId(String clientId) {
		Objects.requireNonNull(clientId, "clientId cannot be null");
        if (clientId.isEmpty()) {
            throw new IllegalArgumentException("clientId cannot be empty");
        }
		this.clientId = clientId;
	}

	public ProfileStatus getProfileStatus() {
		return profileStatus;
	}

	public void setProfileStatus(ProfileStatus profileStatus) {
		Objects.requireNonNull(profileStatus, "profileStatus cannot be null");
		this.profileStatus = profileStatus;
	}

}