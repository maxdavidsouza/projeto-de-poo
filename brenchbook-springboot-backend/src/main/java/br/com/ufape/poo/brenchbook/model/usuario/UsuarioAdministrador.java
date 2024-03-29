package br.com.ufape.poo.brenchbook.model.usuario;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
	
	public void anunciarGlobalmente(Mensagem mensagem, List<UsuarioCadastrado> usuarios) {
		try {
			mensagem.setTitulo("Anúncio Global!");
			mensagem.setDestinatario("Todos os usuários do aplicativo.");
			this.getChat().add(mensagem);
			for(int i = 0; i < usuarios.size(); i++) {
				Mensagem novaMensagem = new Mensagem(mensagem.getTitulo(), mensagem.getConteudo());
				novaMensagem.setRemetente(this.getNome());
				novaMensagem.setDestinatario(usuarios.get(i).getNome());
				usuarios.get(i).getChat().add(novaMensagem);
			}
		} catch (EmptyFieldException e) { e.printStackTrace(); }
	}

}
