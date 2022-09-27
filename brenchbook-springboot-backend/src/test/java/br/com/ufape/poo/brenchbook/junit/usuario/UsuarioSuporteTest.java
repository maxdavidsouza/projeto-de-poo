package br.com.ufape.poo.brenchbook.junit.usuario;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.ufape.poo.brenchbook.exception.comunicacao.EmptyFieldException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidEmailException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidPasswordException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidUsernameException;
import br.com.ufape.poo.brenchbook.exception.usuario.RecommendedAgeException;
import br.com.ufape.poo.brenchbook.model.login.Login;
import br.com.ufape.poo.brenchbook.model.usuario.UsuarioCadastrado;
import br.com.ufape.poo.brenchbook.model.usuario.UsuarioSuporte;

class UsuarioSuporteTest {

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
	
	@Test
	public void bloquearUsuarioTest() throws ParseException, InvalidUsernameException, InvalidPasswordException, InvalidEmailException, EmptyFieldException, RecommendedAgeException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		UsuarioCadastrado temp = this.gerarUsuarioAleatorio();
		UsuarioSuporte usuario1 = new UsuarioSuporte(temp.getNome(), dateFormat.format(temp.getDataDeNascimento()), temp.getBiografia(), temp.getLogin());
		UsuarioCadastrado usuario2 = this.gerarUsuarioAleatorio();
		usuario1.bloquearUsuario(usuario2);
		assertEquals(usuario2.isAtivo(), false);
	}
	
	@Test
	public void bloquearObraTest() {
		
	}

}
