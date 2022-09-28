package br.com.ufape.poo.brenchbook.junit.usuario;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.ufape.poo.brenchbook.exception.comunicacao.EmptyFieldException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidEmailException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidPasswordException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidUsernameException;
import br.com.ufape.poo.brenchbook.exception.usuario.RecommendedAgeException;
import br.com.ufape.poo.brenchbook.model.comunicacao.Mensagem;
import br.com.ufape.poo.brenchbook.model.login.Login;
import br.com.ufape.poo.brenchbook.model.usuario.UsuarioAdministrador;
import br.com.ufape.poo.brenchbook.model.usuario.UsuarioCadastrado;

/**
 * Coleção de Testes da Classe UsuarioAdministrador.
 * @author Max_David
 *
 */
class UsuarioAdministradorTest {

	private Login gerarLoginAleatorio() throws InvalidUsernameException, InvalidPasswordException, InvalidEmailException {
		Faker faker = new Faker();
		String usuario, senha, email;
		usuario = faker.name().firstName();
		senha = faker.internet().password() + "F$";
		email = faker.internet().emailAddress();
		Login login = new Login(usuario, senha, email);
		return login;
	}
	
	private UsuarioCadastrado gerarUsuarioAleatorio() throws ParseException, InvalidUsernameException, InvalidPasswordException, InvalidEmailException, EmptyFieldException, RecommendedAgeException {
		Faker faker = new Faker();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //convesão do tipo Date para uma entrada específica de String
		String nome, dataDeNascimento, biografia;
		nome = faker.name().fullName();
		dataDeNascimento = dateFormat.format(faker.date().birthday());
		//dataDeNascimento = "03/06/2011";
		biografia = faker.lorem().sentence();
		UsuarioCadastrado usuario = new UsuarioCadastrado(nome, dataDeNascimento, biografia, this.gerarLoginAleatorio());
		return usuario;
	}
	
	private UsuarioAdministrador gerarUsuarioAdministradorAleatorio() throws ParseException, InvalidUsernameException, InvalidPasswordException, InvalidEmailException, EmptyFieldException, RecommendedAgeException {
		Faker faker = new Faker();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //convesão do tipo Date para uma entrada específica de String
		String nome, dataDeNascimento, biografia;
		nome = faker.name().fullName();
		dataDeNascimento = dateFormat.format(faker.date().birthday());
		//dataDeNascimento = "03/06/2011";
		biografia = faker.lorem().sentence();
		UsuarioAdministrador usuario = new UsuarioAdministrador(nome, dataDeNascimento, biografia, this.gerarLoginAleatorio());
		return usuario;
	}
	
	private Mensagem gerarMensagemAleatoria(UsuarioCadastrado remetente, UsuarioCadastrado destinatario) throws EmptyFieldException {
		Faker faker = new Faker();
		String titulo, conteudo;
		titulo = faker.lorem().sentence();
		conteudo = faker.lorem().paragraph();
		Mensagem mensagem = new Mensagem(remetente.getNome(), titulo, conteudo, destinatario.getNome()); 
		return mensagem;
	}
	
	/**
	 * Teste para Verificar se Anúncios Globais são Efetuados pelo Administrador
	 */
	@Test
	void anunciarGlobalmenteTest() {
		try {
			UsuarioAdministrador novoUsuarioAdmin = this.gerarUsuarioAdministradorAleatorio();
			List<UsuarioCadastrado> listaDeUsuariosCadastrados = new ArrayList<UsuarioCadastrado>();
			for(int i = 0; i < 3; i++)
				listaDeUsuariosCadastrados.add(this.gerarUsuarioAleatorio());
			Mensagem mensagemAleatoria = this.gerarMensagemAleatoria(novoUsuarioAdmin, novoUsuarioAdmin);
			novoUsuarioAdmin.anunciarGlobalmente(mensagemAleatoria, listaDeUsuariosCadastrados);
			for(int i = 0; i < listaDeUsuariosCadastrados.size(); i++) {
				System.out.println(listaDeUsuariosCadastrados.get(i).toString());
				System.out.println(listaDeUsuariosCadastrados.get(i).getChat().get(0).toString());
			}
			
		} catch (ParseException | InvalidUsernameException | InvalidPasswordException | InvalidEmailException | EmptyFieldException | RecommendedAgeException e) {
			e.printStackTrace();
		}
		
	}

}
