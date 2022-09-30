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
import br.com.ufape.poo.brenchbook.model.usuario.UsuarioSuporte;
import br.com.ufape.poo.brenchbook.repository.RepositorioLogin;
import br.com.ufape.poo.brenchbook.repository.RepositorioUsuarioSuporte;

/**
 * Controlador de um Usuário Suporte
 * @author Diogo_Silva
 *
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class ControllerUsuarioSuporte {
	
	@Autowired
	private RepositorioUsuarioSuporte repositorioUsuarioSuporte;
	
	@Autowired
	private RepositorioLogin repositorioLogin;
	
	@GetMapping("/usuarios-suporte")
	public List<UsuarioSuporte> listarUsuarioSuporte(){
		return repositorioUsuarioSuporte.findAll();
	}
	
	@PostMapping("/usuarios-suporte")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioSuporte salvarUsuarioSuporte(@RequestBody UsuarioSuporte usuarioSuporte) throws DuplicatedUsuarioException, DuplicatedEmailException {
		String usuario = usuarioSuporte.getLogin().getUsuario();
		String email = usuarioSuporte.getLogin().getEmail();
		if(!repositorioLogin.findByUsuario(usuario).isEmpty())
			throw new DuplicatedUsuarioException(usuarioSuporte.getLogin().getUsuario());
		if(!repositorioLogin.findByEmail(email).isEmpty())
			throw new DuplicatedEmailException(usuarioSuporte.getLogin().getEmail());
		return repositorioUsuarioSuporte.save(usuarioSuporte);
	}

}
