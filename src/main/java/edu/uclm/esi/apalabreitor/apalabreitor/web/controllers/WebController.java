package edu.uclm.esi.apalabreitor.apalabreitor.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.uclm.esi.apalabreitor.apalabreitor.dao.UserRepository;
import edu.uclm.esi.apalabreitor.apalabreitor.model.Match;
import edu.uclm.esi.apalabreitor.apalabreitor.model.User;
import edu.uclm.esi.apalabreitor.apalabreitor.web.exceptions.LoginException;

@RestController
public class WebController {
	@Autowired
	private UserRepository userRepo;
	private List<User> users = new ArrayList<>();
	private List<Match> pendingMatches = new ArrayList<>();
	private ConcurrentHashMap<String , Match> inPlaymatches = new ConcurrentHashMap<>();
	
	//@RequestMapping(name="solicitarPartida", method = RequestMethod.POST)
	@PostMapping("/solicitarPartida")
	public String solicitarPartida(HttpSession session, @RequestParam(value="action") String action) throws Exception {
		if (session.getAttribute("user")==null)
			throw new Exception("Identificate antes de solicitar");
		User user = (User) session.getAttribute("user");
		if(action.equals("Nueva partida")) {
			Match match = new Match();
			match.setPlayerA(user);
			this.pendingMatches.add(match);
			
		}else if (action.equals("Unir a partida")) {
			Match match = this.pendingMatches.remove(0);
			match.setPlayerB(user);
			this.inPlaymatches.put(match.getId(),match);
		}
		return "hola, has elegido" + action;
	}
	
	@RequestMapping("/listaUsuarios")
	public List<User> listaUsuarios() {
		return this.users;
	}
	
	@RequestMapping("/register")
	public void register(@RequestParam(value="userName") String userName, @RequestParam(value="email") String email, @RequestParam(value="pwd1") String pwd1, @RequestParam(value="pwd2") String pwd2) throws Exception {
		if (pwd1==null || pwd2==null)
			throw new Exception("Empty passwords");
		if (!pwd1.equals(pwd2))
			throw new Exception("Passwords do not match");
		if (userRepo.findById(userName).isPresent() || userRepo.findByEmail(email)!=null)
			throw new Exception("The user already exists");
		User user=new User();
		user.setEmail(email);
		user.setUserName(userName);
		user.setPwd(pwd1);
		userRepo.save(user);
	}
	
	@RequestMapping(value="/login")
	public void login(HttpSession session, @RequestParam(value="userName") String userName, @RequestParam(value="pwd") String pwd, @RequestParam boolean withEmail) throws LoginException {
		User user;
		if (withEmail)
			user=userRepo.findByEmail(userName);
		else
			user=userRepo.findById(userName).get();
		if (user!=null && user.getPwd().equals(pwd)) {
			session.setAttribute("user", user);
			
			this.users.add(user);
		}
		else throw new LoginException();
	}
	
	
	@RequestMapping("/salir")
	public void salir(HttpSession session) throws Exception {
		session.invalidate();
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(HttpServletRequest req, Exception ex) {
		ModelAndView result = new ModelAndView();
		result.setViewName("respuesta");
		result.addObject("exception", ex);
		result.setStatus(HttpStatus.BAD_REQUEST);
	    return result;
	}
	
	@ExceptionHandler(LoginException.class)
	public ModelAndView handleLoginException(HttpServletRequest req, Exception ex) {
		ModelAndView result = new ModelAndView();
		result.setViewName("mensajeLogin");
		result.addObject("exception", ex);
		result.setStatus(HttpStatus.UNAUTHORIZED);
	    return result;
	}
	
}
