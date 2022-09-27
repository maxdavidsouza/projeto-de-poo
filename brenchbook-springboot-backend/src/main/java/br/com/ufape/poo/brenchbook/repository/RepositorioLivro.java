package br.com.ufape.poo.brenchbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ufape.poo.brenchbook.model.biblioteca.Biblioteca;
import br.com.ufape.poo.brenchbook.model.livro.Livro;
import br.com.ufape.poo.brenchbook.model.usuario.UsuarioCadastrado;

/**
 * Repositório de Livros
 * @author Max_David
 *
 */
@Repository
public interface RepositorioLivro extends JpaRepository<Livro, Long> {
	List<Livro> findByUsuario(UsuarioCadastrado usuario);
	List<Livro> findByBiblioteca(Biblioteca biblioteca);
}
