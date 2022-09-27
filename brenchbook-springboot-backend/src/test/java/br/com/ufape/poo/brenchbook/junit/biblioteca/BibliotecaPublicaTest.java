package br.com.ufape.poo.brenchbook.junit.biblioteca;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.com.ufape.poo.brenchbook.exception.comunicacao.EmptyFieldException;
import br.com.ufape.poo.brenchbook.model.biblioteca.BibliotecaPublica;
import br.com.ufape.poo.brenchbook.model.livro.Livro;

/**
 * Coleção de Testes das Classes Biblioteca, BibliotecaPrivada e BibliotecaPublica
 * @author Diogo_Silva
 *
 */
class BibliotecaPublicaTest {

	/**
	 * Teste unitário de Inserção de Livros em Bibliotecas
	 * @throws EmptyFieldException
	 */
	@Test
	public void testeInsercaoLivro() throws EmptyFieldException {
		BibliotecaPublica bibliotecaPublica = new BibliotecaPublica();
		Livro livro = new Livro("Chorão","Marquinhos","Conta da vida de Chorão...","Ação");
		bibliotecaPublica.adicionarLivro(livro);
		assertEquals(bibliotecaPublica.getLivros().get(0), livro);
	}

}
