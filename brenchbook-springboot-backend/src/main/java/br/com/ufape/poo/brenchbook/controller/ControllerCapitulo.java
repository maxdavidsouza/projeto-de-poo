package br.com.ufape.poo.brenchbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufape.poo.brenchbook.model.livro.Capitulo;
import br.com.ufape.poo.brenchbook.repository.RepositorioCapitulo;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class ControllerCapitulo {
	
	@Autowired
	private RepositorioCapitulo repositorioCapitulo;
	
	@GetMapping("/capitulo")
	public List<Capitulo> listarCapitulo(){
		return repositorioCapitulo.findAll();
	}
}
