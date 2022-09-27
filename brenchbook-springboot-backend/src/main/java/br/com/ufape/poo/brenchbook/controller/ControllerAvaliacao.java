package br.com.ufape.poo.brenchbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.ufape.poo.brenchbook.model.comunicacao.Avaliacao;
import br.com.ufape.poo.brenchbook.repository.RepositorioAvaliacao;

public class ControllerAvaliacao {
	@Autowired
	private RepositorioAvaliacao repositorioAvaliacao;
	
	@GetMapping("/avaliacao")
	public List<Avaliacao> listarBiblioteca(){
		return repositorioAvaliacao.findAll();
	}
	
	@PostMapping("/avaliacao")
	@ResponseStatus(HttpStatus.CREATED)
	public Avaliacao criarAvaliacao() {
		return null;
		
	}
}
