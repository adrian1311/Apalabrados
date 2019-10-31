package edu.uclm.esi.apalabreitor.apalabreitor.model;

import java.util.UUID;

public class Match {
	private String id;
	private User PlayerA;
	private User PlayerB;
	
	//Test
	public Match() {
		this.id = UUID.randomUUID().toString();
	}

	public void setPlayerA(User user) {
		this.PlayerA= user;
		// TODO Auto-generated method stub
		
	}
	public void setPlayerB(User user) {
		this.PlayerB= user;
		// TODO Auto-generated method stub
		
	}

	public String getId() {
		return id;
	}
}
