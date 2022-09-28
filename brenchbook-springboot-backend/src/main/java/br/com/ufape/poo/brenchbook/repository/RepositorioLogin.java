package br.com.ufape.poo.brenchbook.repository;

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
	
}
