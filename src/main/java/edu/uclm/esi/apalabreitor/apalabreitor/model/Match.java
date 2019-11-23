package edu.uclm.esi.apalabreitor.apalabreitor.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Match {
	private String id;
	private User playerA;
	private User playerB;
	private User jugadorConElTurno;
	private Board board;
	
	public Match() {
		this.id = UUID.randomUUID().toString();
	}

	public void setPlayerA(User user) {
		this.playerA = user;
	}

	public void setPlayerB(User playerB) {
		this.playerB = playerB;
	}
	
	public String getId() {
		return id;
	}

	public void start() {
		this.jugadorConElTurno = new Random().nextBoolean() ? this.playerA : this.playerB;
		this.board = new Board();
		
		try {
			JSONObject jsaA = new JSONObject();
			jsaA.put("type", "START");
			jsaA.put("letras", this.board.getLetters(7));
			jsaA.put("turno", jugadorConElTurno==playerA ? true : false);
			this.playerA.sendMessage(jsaA.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			
		}
		try {
			JSONObject jsaB = new JSONObject();
			jsaB.put("type", "START");
			jsaB.put("letras", this.board.getLetters(7));
			jsaB.put("turno", jugadorConElTurno==playerB ? true : false);
			this.playerB.sendMessage(jsaB.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			
		}
	}

	public void playerPlays(String idSession, JSONArray jsaJugada) throws Exception {
		ResultadoJugada resultado;
		User player = this.playerA.getSession().getId().equals(idSession) ? playerA : playerB;
		if (player!=this.jugadorConElTurno) {
			resultado = new ResultadoJugada();
			resultado.addException("No tienes el turno");
			player.sendMessage(resultado);
		} else {//en caso de que el jugador si que tenga el turno
			ArrayList<JSONObject> jugada = new ArrayList<>();
			for (int i=0; i<jsaJugada.length(); i++)
				jugada.add(jsaJugada.getJSONObject(i));
			resultado = this.board.movement(jugada);
			
			if(resultado.getExceptions().isEmpty() && resultado.invalid().isEmpty()) {//comprobamos que todo este correcto y no tengamos errores
				resultado.setTurno(false);
				player.sendMessage(resultado);
				User contrincante = this.playerA==player ? playerB : playerA;
				resultado.ocultarLetras();
				resultado.setTurno(true);
				
				contrincante.sendMessage(resultado);
				cambiarTurno();
			}else {
				player.sendMessage(resultado);//en caso de error, mandamos el error al jugador que lo ha generado
			}
			
			
		}
	}

	private void cambiarTurno() {
		this.jugadorConElTurno = (this.playerA==this.jugadorConElTurno ? this.playerB : this.playerA);
	}
}
