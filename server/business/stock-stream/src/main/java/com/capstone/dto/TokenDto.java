package com.capstone.dto;

import java.util.Objects;

public class TokenDto {
	private Long token;

	public TokenDto() {
		super();
	}

	public TokenDto(Long token) {
		super();
		this.token = token;
	}

	public Long getToken() {
		return token;
	}

	public void setToken(Long token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		return Objects.hash(token);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenDto other = (TokenDto) obj;
		return Objects.equals(token, other.token);
	}

	@Override
	public String toString() {
		return "TokenDto [token=" + token + "]";
	}

}
