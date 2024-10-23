package com.capstone.dto;

import java.util.Objects;


public class HttpErrorDto {
	private String timestamp;
	private int status;
	private String error;
	private String path;

	public HttpErrorDto() {
		super();
	}

	public HttpErrorDto(String timestamp, int status, String error, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.path = path;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int hashCode() {
		return Objects.hash(error, path, status, timestamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HttpErrorDto other = (HttpErrorDto) obj;
		return Objects.equals(error, other.error) && Objects.equals(path, other.path) && status == other.status
				&& Objects.equals(timestamp, other.timestamp);
	}

	@Override
	public String toString() {
		return "HttpErrorDto [timestamp=" + timestamp + ", status=" + status + ", error=" + error + ", path=" + path
				+ "]";
	}

}
