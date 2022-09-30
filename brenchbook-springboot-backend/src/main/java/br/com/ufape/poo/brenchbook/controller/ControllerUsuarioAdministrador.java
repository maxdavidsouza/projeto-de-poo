package br.com.ufape.poo.brenchbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufape.poo.brenchbook.exception.login.DuplicatedEmailException;
import br.com.ufape.poo.brenchbook.exception.login.DuplicatedUsuarioException;
import br.com.ufape.poo.brenchbook.model.usuario.UsuarioAdministrador;
import br.com.ufape.poo.brenchbook.repository.RepositorioLogin;
import br.com.ufape.poo.brenchbook.repository.RepositorioUsuarioAdministrador;

/**
 * Controlador de um Usuário Cadastrado
 * @author Max_David
 *
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class ControllerUsuarioAdministrador {

	@Autowired
	private RepositorioUsuarioAdministrador repositorioUsuarioAdministrador;
	
	@Autowired
	private RepositorioLogin repositorioLogin;
	
	@GetMapping("/usuarios-administrador")
	public List<UsuarioAdministrador> listarUsuarioSuporte(){
		return repositorioUsuarioAdministrador.findAll();
	}
	
	@PostMapping("/usuarios-administrador")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioAdministrador salvarUsuarioSuporte(@RequestBody UsuarioAdministrador usuarioAdministrador) throws DuplicatedUsuarioException, DuplicatedEmailException {
		String usuario = usuarioAdministrador.getLogin().getUsuario();
		String email = usuarioAdministrador.getLogin().getEmail();
		if(!repositorioLogin.findByUsuario(usuario).isEmpty())
			throw new DuplicatedUsuarioException(usuarioAdministrador.getLogin().getUsuario());
		if(!repositorioLogin.findByEmail(email).isEmpty())
			throw new DuplicatedEmailException(usuarioAdministrador.getLogin().getEmail());
		return repositorioUsuarioAdministrador.save(usuarioAdministrador);
	}
}
