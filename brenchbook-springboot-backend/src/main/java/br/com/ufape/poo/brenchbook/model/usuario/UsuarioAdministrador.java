package br.com.ufape.poo.brenchbook.model.usuario;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.ufape.poo.brenchbook.exception.comunicacao.EmptyFieldException;
import br.com.ufape.poo.brenchbook.exception.usuario.RecommendedAgeException;
import br.com.ufape.poo.brenchbook.model.comunicacao.Mensagem;
import br.com.ufape.poo.brenchbook.model.login.Login;

/**
 * Classe Básica de Negócio - Usuario Administrador (Super-Usuário)
 * @author Pedro_Augusto & Diogo_Silva
 *
 */
@Entity(name = "UsuarioAdministrador")
@Table(name = "usuario_administrador")
public class UsuarioAdministrador extends UsuarioSuporte {
	
	public UsuarioAdministrador() {
		super();
	}
	
	public UsuarioAdministrador(String nome, String dataDeNascimento, String biografia, Login login) throws ParseException, EmptyFieldException, RecommendedAgeException {
		super(nome, dataDeNascimento, biografia, login);
	}
	
	public UsuarioSuporte promoverUsuario(UsuarioCadastrado usuario) throws ParseException, EmptyFieldException, RecommendedAgeException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		String strDataDeNascimento = dateFormat.format(usuario.getDataDeNascimento());
		UsuarioSuporte usuarioSuporte = new UsuarioSuporte(usuario.getNome(),strDataDeNascimento, usuario.getBiografia(), usuario.getLogin());
		return usuarioSuporte;
	}
	
	public void removerUsuario(UsuarioCadastrado usuario) {
		
	}
	
	public void anunciarGlobalmente(Mensagem mensagem) {
		
	}

}
