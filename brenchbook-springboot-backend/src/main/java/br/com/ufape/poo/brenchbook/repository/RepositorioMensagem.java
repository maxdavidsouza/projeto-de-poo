package br.com.ufape.poo.brenchbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ufape.poo.brenchbook.model.comunicacao.Mensagem;
import br.com.ufape.poo.brenchbook.model.usuario.UsuarioCadastrado;

/**
 * Repositório de Mensagens
 * @author Max_David
 *
 */
@Repository
public interface RepositorioMensagem extends JpaRepository<Mensagem, Long> {
	List<Mensagem> findByUsuarioRemetente(UsuarioCadastrado usuarioRemetente);
}
