package br.com.ufape.poo.brenchbook.junit.livro;


import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.ufape.poo.brenchbook.exception.comunicacao.EmptyFieldException;
import br.com.ufape.poo.brenchbook.exception.livro.InvalidAuthorException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidEmailException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidPasswordException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidUsernameException;
import br.com.ufape.poo.brenchbook.exception.usuario.RecommendedAgeException;
import br.com.ufape.poo.brenchbook.model.livro.Capitulo;
import br.com.ufape.poo.brenchbook.model.livro.Livro;
import br.com.ufape.poo.brenchbook.model.login.Login;
import br.com.ufape.poo.brenchbook.model.usuario.UsuarioCadastrado;

/**
 * Coleção de Testes das Classes: Livro, Capitulo e Avaliação (User-2-Content) do Sistema.
 * @author Diogo_Silva
 *
 */
class LivroTest {

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
	
	private Livro gerarLivroAleatorio(String nomeDoUsuario) throws EmptyFieldException {
		Faker faker = new Faker();
		String titulo, resenha, categoria; 
		titulo = faker.lorem().sentence();
		resenha = faker.lorem().paragraph();
		categoria = faker.lorem().word();
		Livro livro = new Livro(titulo, resenha, categoria);
		livro.setAutor(nomeDoUsuario);
		return livro;
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
	 * Teste Unitário + Integração de Criação de Livros (Usuário que cria Livros)
	 * @throws EmptyFieldException
	 * @throws ParseException
	 * @throws InvalidUsernameException
	 * @throws InvalidPasswordException
	 * @throws InvalidEmailException
	 * @throws RecommendedAgeException
	 */
	@Test
	public void criarLivro() throws EmptyFieldException, ParseException, InvalidUsernameException, InvalidPasswordException, InvalidEmailException, RecommendedAgeException {
		UsuarioCadastrado usuario = this.gerarUsuarioAleatorio();
		Livro livro = this.gerarLivroAleatorio(usuario.getNome());
		usuario.getBibliotecaPrivada().adicionarLivro(livro);
		System.out.println(usuario.toString()+"\n"+
							"Contém o(s) Livro(s) a seguir na sua biblioteca: \n"+
								usuario.getBibliotecaPrivada().getLivros().get(0).toString());
	}
	
	/**
	 * Teste Unitário de Criação de Capítulos em Livros
	 * @throws EmptyFieldException
	 * @throws InvalidAuthorException
	 * @throws ParseException
	 * @throws InvalidUsernameException
	 * @throws InvalidPasswordException
	 * @throws InvalidEmailException
	 * @throws RecommendedAgeException
	 */
	@Test
	public void escreverCapitulo() throws EmptyFieldException, InvalidAuthorException, ParseException, InvalidUsernameException, InvalidPasswordException, InvalidEmailException, RecommendedAgeException {
		Livro livro = this.gerarLivroAleatorio("Autor");
		Capitulo capitulo = this.gerarCapituloAleatorio();
		livro.adicionarCapitulo(capitulo);
		assertEquals(capitulo, livro.getCapitulos().get(0));
	}
	
	/**
	 * Teste Unitário de Sobrescrita de Capítulos em Livros
	 * @throws EmptyFieldException
	 * @throws InvalidAuthorException
	 * @throws ParseException
	 * @throws InvalidUsernameException
	 * @throws InvalidPasswordException
	 * @throws InvalidEmailException
	 * @throws RecommendedAgeException
	 */
	@Test
	public void sobrescreverCapitulo() throws EmptyFieldException, InvalidAuthorException, ParseException, InvalidUsernameException, InvalidPasswordException, InvalidEmailException, RecommendedAgeException {
		Livro livro = this.gerarLivroAleatorio("Autor");
		Capitulo capitulo = this.gerarCapituloAleatorio();
		Capitulo novo_capitulo = this.gerarCapituloAleatorio();
		livro.adicionarCapitulo(capitulo);
		livro.getCapitulos().set(0, novo_capitulo);
		assertEquals(novo_capitulo, livro.getCapitulos().get(0));
	}

}
