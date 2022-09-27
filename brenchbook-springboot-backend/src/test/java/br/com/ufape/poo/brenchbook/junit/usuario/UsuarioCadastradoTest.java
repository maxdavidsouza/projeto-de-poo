package br.com.ufape.poo.brenchbook.junit.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import br.com.ufape.poo.brenchbook.exception.comunicacao.EmptyFieldException;
import br.com.ufape.poo.brenchbook.exception.comunicacao.InvalidEvaluationException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidEmailException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidPasswordException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidUsernameException;
import br.com.ufape.poo.brenchbook.exception.usuario.RecommendedAgeException;
import br.com.ufape.poo.brenchbook.model.comunicacao.Avaliacao;
import br.com.ufape.poo.brenchbook.model.comunicacao.Mensagem;
import br.com.ufape.poo.brenchbook.model.livro.Capitulo;
import br.com.ufape.poo.brenchbook.model.login.Login;
import br.com.ufape.poo.brenchbook.model.usuario.UsuarioCadastrado;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

/**
 * Coleção de Testes da Classe UsuarioCadastrado.
 * @author Max_David
 *
 */
class UsuarioCadastradoTest {

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
	
	private Mensagem gerarMensagemAleatoria(UsuarioCadastrado remetente, UsuarioCadastrado destinatario) throws EmptyFieldException {
		Faker faker = new Faker();
		String titulo, conteudo;
		titulo = faker.lorem().sentence();
		conteudo = faker.lorem().paragraph();
		Mensagem mensagem = new Mensagem(remetente.getNome(), titulo, conteudo, destinatario.getNome()); 
		return mensagem;
	}
	
	private Avaliacao gerarAvaliacaoAleatoria(String nomeDoUsuario) throws InvalidEvaluationException, EmptyFieldException {
		Faker faker = new Faker();
		String comentario;
		int nota;
		comentario = faker.lorem().sentence();
		nota = (int) (Math.random() * 11);
		Avaliacao avaliacao = new Avaliacao(nomeDoUsuario, comentario, nota);
		return avaliacao;
	}
	
	private Capitulo gerarCapituloAleatorio() throws EmptyFieldException {
		Faker faker = new Faker();
		String titulo, escopo;
		titulo = faker.lorem().sentence();
		escopo = faker.lorem().paragraph();
		Capitulo capitulo = new Capitulo(titulo, escopo);
		return capitulo;
	}
	
	/**
	 * Teste de Integração: Comunicação entre Dois Usuários devidamente Cadastrados no Sistema
	 * @throws ParseException tratamento de exceção de conversão de data/string
	 * @throws InvalidUsernameException tratamento de exceção de nome inválido de login
	 * @throws InvalidPasswordException tratamento de exceção de senha inválida de login
	 * @throws InvalidEmailException tratamento de exceção de email inválido de login
	 * @throws EmptyFieldException tratamento de exceção de campos vazios
	 * @throws RecommendedAgeException tratamento de exceção de idade recomendada para uso do sistema
	 */
	@Test
	public void enviarMensagemUsuarioTest() throws ParseException, InvalidUsernameException, InvalidPasswordException, InvalidEmailException, EmptyFieldException, RecommendedAgeException {
		UsuarioCadastrado usuario1 = this.gerarUsuarioAleatorio();
		UsuarioCadastrado usuario2 = this.gerarUsuarioAleatorio();
		usuario1.enviarMensagem(this.gerarMensagemAleatoria(usuario1, usuario2), usuario2);
		System.out.println(usuario2.getChat().get(0).toString());
	}
	
	/**
	 * Teste Unitário: envio de avaliação para Capítulos de livros quaisquer. 
	 * @throws EmptyFieldException
	 * @throws ParseException
	 * @throws InvalidUsernameException
	 * @throws InvalidPasswordException
	 * @throws InvalidEmailException
	 * @throws RecommendedAgeException
	 * @throws InvalidEvaluationException
	 */
	@Test
	public void enviarAvaliacaoUsuarioTest() throws EmptyFieldException, ParseException, InvalidUsernameException, InvalidPasswordException, InvalidEmailException, RecommendedAgeException, InvalidEvaluationException {
		UsuarioCadastrado usuario = this.gerarUsuarioAleatorio();
		Capitulo capitulo = this.gerarCapituloAleatorio();
		Avaliacao avaliacao = this.gerarAvaliacaoAleatoria(usuario.getNome());
		usuario.enviarAvaliacao(avaliacao, capitulo);
		assertEquals(capitulo.getAvaliacao().get(0), avaliacao);
	}

}
