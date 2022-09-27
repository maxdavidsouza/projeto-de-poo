package br.com.ufape.poo.brenchbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufape.poo.brenchbook.model.login.Login;
import br.com.ufape.poo.brenchbook.repository.RepositorioLogin;

/**
 * Controlador de Login
 * @author Pedro_Augusto
 *
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class ControllerLogin {
	
	@Autowired
	private RepositorioLogin repositorioLogin;
	
	@GetMapping("/login")
	public List<Login> listarLogin(){
		return repositorioLogin.findAll();
	}
	
}
