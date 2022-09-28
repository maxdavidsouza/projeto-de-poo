package br.com.ufape.poo.brenchbook.model.usuario;

import java.text.ParseException;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.ufape.poo.brenchbook.exception.comunicacao.EmptyFieldException;
import br.com.ufape.poo.brenchbook.exception.usuario.RecommendedAgeException;
import br.com.ufape.poo.brenchbook.model.livro.Livro;
import br.com.ufape.poo.brenchbook.model.login.Login;

/**
 * Classe Básica de Negócio - Usuário Suporte (Usuário-Plus)
 * @author Pedro_Augusto & Max_David
 *
 */
@Entity(name = "UsuarioSuporte")
@Table(name = "usuario_suporte")
public class UsuarioSuporte extends UsuarioCadastrado {

	public UsuarioSuporte() {
		super();
	}
	
	public UsuarioSuporte(String nome, String dataDeNascimento, String biografia, Login login) throws ParseException, EmptyFieldException, RecommendedAgeException {
		super(nome, dataDeNascimento, biografia, login);
	}
	
	public void bloquearUsuario(UsuarioCadastrado usuario) {
		usuario.setAtivo(false);
	}
	
	public void bloquearLivro(Livro livro) {
		livro.setEstadoDePublicacao("Bloqueada");
	}
	
	public void desbloquearUsuario(UsuarioCadastrado usuario) {
		usuario.setAtivo(true);
	}
	
	public void desbloquearLivro(Livro livro) {
		livro.setEstadoDePublicacao("Privado");
	}

}
