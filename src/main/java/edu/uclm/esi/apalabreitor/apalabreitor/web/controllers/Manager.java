package edu.uclm.esi.apalabreitor.apalabreitor.web.controllers;

import edu.uclm.esi.apalabreitor.apalabreitor.model.*;

public class Manager {
	
	private Manager() {
	}
	
	private static class ManagerHolder {
		static Manager singleton=new Manager();
	}
	
	public static Manager get() {
		return ManagerHolder.singleton;
	}


}
