package com.patilluxuries.responce;

public class AuthResponce {
	
	private String jwt;
	
	private String message;

	public AuthResponce(String jwt, String message) {
		super();
		this.jwt = jwt;
		this.message = message;
	}
	
	

	public AuthResponce() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
