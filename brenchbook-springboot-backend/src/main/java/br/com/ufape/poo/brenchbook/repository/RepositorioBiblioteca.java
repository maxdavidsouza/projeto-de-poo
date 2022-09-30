package br.com.ufape.poo.brenchbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ufape.poo.brenchbook.model.biblioteca.Biblioteca;

/**
 * Repositório de Bibliotecas
 * @author Diogo_Silva
 *
 */
@Repository
public interface RepositorioBiblioteca extends JpaRepository<Biblioteca, Long> {
	
}
