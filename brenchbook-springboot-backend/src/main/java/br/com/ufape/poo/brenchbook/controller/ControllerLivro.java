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

import br.com.ufape.poo.brenchbook.model.livro.Livro;
import br.com.ufape.poo.brenchbook.repository.RepositorioLivro;

/**
 * Controlador da Classe Livro
 * @author Max_David
 *
 */

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class ControllerLivro {
	
	@Autowired
	private RepositorioLivro repositorioLivro;
	
	@GetMapping("/livro")
	public List<Livro> listarLivro(){
		return repositorioLivro.findAll();
	}
	
	@PostMapping("/livro")
	@ResponseStatus(HttpStatus.CREATED)
	public Livro salvarUsuarioSuporte(@RequestBody Livro livro) {
		return repositorioLivro.save(livro);
	}

}
