package br.com.ufape.poo.brenchbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ufape.poo.brenchbook.model.livro.Capitulo;

/**
 * Repositorio de Capitulos
 * @author Max_David
 *
 */
@Repository
public interface RepositorioCapitulo extends JpaRepository<Capitulo, Long> {

}
