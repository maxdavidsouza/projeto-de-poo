package br.com.ufape.poo.brenchbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ufape.poo.brenchbook.model.livro.Livro;

/**
 * Repositório de Livros
 * @author Max_David
 *
 */
@Repository
public interface RepositorioLivro extends JpaRepository<Livro, Long> {

}
