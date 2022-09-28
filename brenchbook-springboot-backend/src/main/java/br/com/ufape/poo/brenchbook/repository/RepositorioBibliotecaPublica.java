package br.com.ufape.poo.brenchbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ufape.poo.brenchbook.model.biblioteca.BibliotecaPublica;

public interface RepositorioBibliotecaPublica extends JpaRepository<BibliotecaPublica, Long>{

}
