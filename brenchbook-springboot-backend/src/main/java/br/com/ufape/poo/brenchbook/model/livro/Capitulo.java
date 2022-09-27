package br.com.ufape.poo.brenchbook.model.livro;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.ufape.poo.brenchbook.exception.comunicacao.EmptyFieldException;
import br.com.ufape.poo.brenchbook.model.comunicacao.Avaliacao;

/**
 * Classe Básica de Negócio - Capítulo (Objeto que faz Composição com o Livro)
 * @author Max_David
 *
 */
@Entity(name = "Capitulo")
@Table(name = "capitulo")
public class Capitulo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_capitulo")
	private long id;
	
	@Column(name = "titulo", nullable = false)
	private String titulo;
	
	@Column(name = "escopo", columnDefinition = "LONGTEXT", nullable = false)
	private String escopo;
	
	@Column(name = "pontuacao")
	private int pontuacao;
	
	@ManyToOne
	@JoinColumn(name = "id_livro") //um capítulo recebe uma chave estrangeira de um livro
	private Livro livro;
	
	@OneToMany
	@JoinColumn(name = "id_capitulo")
	private List<Avaliacao> avaliacao;
	
	public Capitulo() {
		this.avaliacao = new ArrayList<Avaliacao>();
	}
	
	public Capitulo(String titulo, String escopo) throws EmptyFieldException {
		if(titulo.isBlank() || titulo.isEmpty())
			throw new EmptyFieldException("Titulo");
		if(escopo.isBlank() || escopo.isEmpty())
			throw new EmptyFieldException("Escopo");
		this.titulo = titulo;
		this.escopo = escopo;
		this.pontuacao = 0;
		this.avaliacao = new ArrayList<Avaliacao>();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getEscopo() {
		return escopo;
	}

	public void setEscopo(String escopo) {
		this.escopo = escopo;
	}

	public int getPontuacao() {
		return pontuacao;
	}
	
	public List<Avaliacao> getAvaliacao() {
		return avaliacao;
	}

	public void atualizarPontuacao() {
		for(int i = 0; i < this.avaliacao.size(); i++)
			this.pontuacao += this.avaliacao.get(i).getNotaDoUsuario();
	}
	
	public Livro getLivro() {
		return livro;
	}

	/**
	 * Método de Impressão de Dados para Debug de Código
	 */
	@Override
	public String toString() {
		return "Capitulo [id: " + id + "]{\n, Titulo = " + titulo + "\n, Conteúdo = " + escopo + "\n, Nota = " + pontuacao 
				+ "\n, Livro = " + livro.getTitulo() + "\n, Autor = " + livro.getAutor() + "\n}";
	}
	
}
