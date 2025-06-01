package com.auth;

public class User {
	private String username;
	private String password;
	
	public User() {}
	
	//构造方法
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	//Getter&Setter
	public String getUsername() {
		return username;
	}
	
	public void setId(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}


