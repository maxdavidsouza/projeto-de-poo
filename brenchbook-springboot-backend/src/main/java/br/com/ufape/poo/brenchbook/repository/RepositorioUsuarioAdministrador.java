package br.com.ufape.poo.brenchbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ufape.poo.brenchbook.model.usuario.UsuarioAdministrador;

/**
 * Repositório de Usuários Administradores
 * @author Pedro_Augusto
 *
 */
@Repository
public interface RepositorioUsuarioAdministrador extends JpaRepository<UsuarioAdministrador, Long> {

}
