package br.com.ufape.poo.brenchbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ufape.poo.brenchbook.model.usuario.UsuarioSuporte;

/**
 * Repositório de Usuários Suporte
 * @author Pedro_Augusto
 *
 */
@Repository
public interface RepositorioUsuarioSuporte  extends JpaRepository<UsuarioSuporte, Long> {

}
