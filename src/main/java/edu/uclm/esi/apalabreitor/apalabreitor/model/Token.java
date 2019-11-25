package edu.uclm.esi.apalabreitor.apalabreitor.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Token {
	
	@Id	
	private String token;
	private String email;
	private long caducidad;
	
	public Token() {
		
		// TODO Auto-generated constructor stub
	}
	
public Token(String email) {
		
		this.email=email;
		this.token = UUID.randomUUID().toString();
		this.caducidad = System.currentTimeMillis() + 600000;
		// TODO Auto-generated constructor stub
	}

public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public long getCaducidad() {
	return caducidad;
}

public void setCaducidad(long caducidad) {
	this.caducidad = caducidad;
}

public boolean isCaducado() {
	// TODO Auto-generated method stub
	return System.currentTimeMillis()>this.caducidad;
}

	
}
