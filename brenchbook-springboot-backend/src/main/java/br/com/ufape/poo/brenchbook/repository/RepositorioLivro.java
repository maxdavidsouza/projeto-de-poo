package br.com.ufape.poo.brenchbook.repository;

import java.util.Date;
import java.util.List;

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
	List<Livro> findAllByCategoria(String categoria);
	List<Livro> findAllByAutor(String autor);
	List<Livro> findAllByDataDePostagem(Date dataDePostagem);
	List<Livro> findAllByDataDeEdicao(Date dataDeEdicao);
	List<Livro> findAllByEstadoDePublicacao(String estadoDePublicacao);
	List<Livro> findAllByEstadoDeEscrita(String estadoDeEscrita);
	List<Livro> findAllByNota(float nota);
}
