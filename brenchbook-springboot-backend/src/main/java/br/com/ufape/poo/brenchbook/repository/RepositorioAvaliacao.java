package br.com.ufape.poo.brenchbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ufape.poo.brenchbook.model.comunicacao.Avaliacao;

/**
 * Repositório de Avaliações
 * @author Max_David
 *
 */
@Repository
public interface RepositorioAvaliacao extends JpaRepository<Avaliacao, Long> {

}
