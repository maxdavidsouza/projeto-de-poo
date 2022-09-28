package br.com.ufape.poo.brenchbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ufape.poo.brenchbook.model.comunicacao.Mensagem;

/**
 * Repositório de Mensagens
 * @author Max_David
 *
 */
@Repository
public interface RepositorioMensagem extends JpaRepository<Mensagem, Long> {
	
}
