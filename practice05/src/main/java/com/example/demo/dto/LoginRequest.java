package com.example.demo.dto;

public class LoginRequest {

	//Field
	private String id;
	private String pw;
	
	//Constructor
	public LoginRequest() {
		
	}
	
	//Getter and Setter

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
	
	
}
