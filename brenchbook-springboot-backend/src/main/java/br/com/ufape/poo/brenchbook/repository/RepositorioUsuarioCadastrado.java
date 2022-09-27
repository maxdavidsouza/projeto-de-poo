package br.com.ufape.poo.brenchbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ufape.poo.brenchbook.model.usuario.UsuarioCadastrado;

/**
 * Repositório de Usuários
 * @author Pedro_Augusto
 *
 */
@Repository
public interface RepositorioUsuarioCadastrado extends JpaRepository<UsuarioCadastrado, Long> {

}
