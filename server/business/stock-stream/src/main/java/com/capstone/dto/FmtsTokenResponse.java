package com.capstone.dto;

import java.util.Objects;

public class FmtsTokenResponse {
    private int clientid;
    private String email;
    private long token;

    
    public FmtsTokenResponse() {
		super();
	}

	public FmtsTokenResponse(int clientid, String email, long token) {
		super();
		this.clientid = clientid;
		this.email = email;
		this.token = token;
	}

	public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

	@Override
	public int hashCode() {
		return Objects.hash(clientid, email, token);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FmtsTokenResponse other = (FmtsTokenResponse) obj;
		return clientid == other.clientid && Objects.equals(email, other.email) && token == other.token;
	}

	@Override
	public String toString() {
		return "FmtsTokenResponse [clientid=" + clientid + ", email=" + email + ", token=" + token + "]";
	}
    
    
}