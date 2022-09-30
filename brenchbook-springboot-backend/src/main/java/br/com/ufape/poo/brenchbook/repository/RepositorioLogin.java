package br.com.ufape.poo.brenchbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ufape.poo.brenchbook.model.login.Login;

/**
 * Repositório de Logins
 * @author Diogo_Silva
 *
 */
@Repository
public interface RepositorioLogin extends JpaRepository<Login, Long> {
	List<Login> findByUsuario(String usuario);
	List<Login> findByEmail(String email);
}
