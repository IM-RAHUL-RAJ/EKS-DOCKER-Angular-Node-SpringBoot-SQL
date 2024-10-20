package com.capstone.dto;

public class EmailDTO {
    private String email;
    private Long clientId;

    public EmailDTO(String email) {
		super();
		this.clientId = (long) 0;
		this.email = email;
	}
    
    

	public Long getClientId() {
		return clientId;
	}



	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}



	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}