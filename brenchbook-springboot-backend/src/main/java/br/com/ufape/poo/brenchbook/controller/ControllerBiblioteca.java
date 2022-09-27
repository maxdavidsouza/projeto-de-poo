package br.com.ufape.poo.brenchbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufape.poo.brenchbook.model.biblioteca.Biblioteca;
import br.com.ufape.poo.brenchbook.repository.RepositorioBiblioteca;

/**
 * Controlador da Classe Biblioteca
 * @author Max_David
 *
 */

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class ControllerBiblioteca {

	@Autowired
	private RepositorioBiblioteca repositorioBiblioteca;
	
	@GetMapping("/biblioteca")
	public List<Biblioteca> listarBiblioteca(){
		return repositorioBiblioteca.findAll();
	}
}
