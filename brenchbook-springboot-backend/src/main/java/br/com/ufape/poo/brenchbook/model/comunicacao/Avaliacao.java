package br.com.ufape.poo.brenchbook.model.comunicacao;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.ufape.poo.brenchbook.exception.comunicacao.EmptyFieldException;
import br.com.ufape.poo.brenchbook.exception.comunicacao.InvalidEvaluationException;
import br.com.ufape.poo.brenchbook.model.livro.Capitulo;
import br.com.ufape.poo.brenchbook.model.usuario.UsuarioCadastrado;

/**
 * Classe Básica de Negócio - Avaliacao (User-2-Content)
 * @author Diogo_Silva
 *
 */
@Entity(name = "Avaliacao")
@Table(name = "avaliacao")
public class Avaliacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_avaliacao")
	private long id;
	
	@Column(name = "nota_do_usuario")
	private int notaDoUsuario;
	
	@Column(name = "nome_do_usuario")
	private String nomeDoUsuario;
	
	@Column(name = "comentario_do_usuario")
	private String comentarioDoUsuario;
	
	@ManyToOne
	@JoinColumn(name="id_capitulo")
	private Capitulo capitulo;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private UsuarioCadastrado usuarioCadastrado;
	
	public Avaliacao() {
		
	}
	
	public Avaliacao(String nomeDoUsuario, String comentarioDoUsuario, int notaDoUsuario) throws InvalidEvaluationException, EmptyFieldException {
		if(nomeDoUsuario.isBlank() || nomeDoUsuario.isEmpty())
			throw new EmptyFieldException("Nome do usuário");
		if(comentarioDoUsuario.isBlank() || comentarioDoUsuario.isEmpty())
			throw new EmptyFieldException("Escopo do comentário");
		if(notaDoUsuario < 0 || notaDoUsuario > 10)
			throw new InvalidEvaluationException(notaDoUsuario);
		this.nomeDoUsuario = nomeDoUsuario;
		this.comentarioDoUsuario = comentarioDoUsuario;
		this.notaDoUsuario = notaDoUsuario;
	}

	public int getNotaDoUsuario() {
		return notaDoUsuario;
	}

	public void setNotaDoUsuario(int notaDoUsuario) {
		this.notaDoUsuario = notaDoUsuario;
	}

	public String getComentarioDoUsuario() {
		return comentarioDoUsuario;
	}

	public void setComentarioDoUsuario(String comentarioDoUsuario) {
		this.comentarioDoUsuario = comentarioDoUsuario;
	}

	public String getNomeDoUsuario() {
		return nomeDoUsuario;
	}
	
	/**
	 * Método de Impressão de Dados para Debug de Código
	 */
	@Override
	public String toString() {
		return "Avaliacao [id: " + id + "]{\n, Nome do Usuario  = " + nomeDoUsuario + "\n, Comentario = " + comentarioDoUsuario 
				+ "\n, Nota = " + notaDoUsuario 
				+ "\n, Livro = " + capitulo.getLivro().getTitulo() + "\n, Capitulo = " + capitulo.getTitulo() + "\n}";
	}
	
}
