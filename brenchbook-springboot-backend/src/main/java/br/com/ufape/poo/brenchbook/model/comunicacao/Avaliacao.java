package br.com.ufape.poo.brenchbook.model.comunicacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.ufape.poo.brenchbook.exception.comunicacao.EmptyFieldException;
import br.com.ufape.poo.brenchbook.exception.comunicacao.InvalidEvaluationException;

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
	
	public Avaliacao() {}
	
	public Avaliacao(String comentarioDoUsuario, int notaDoUsuario) throws InvalidEvaluationException, EmptyFieldException {
		if(comentarioDoUsuario.isBlank() || comentarioDoUsuario.isEmpty())
			throw new EmptyFieldException("Escopo do comentário");
		if(notaDoUsuario < 0 || notaDoUsuario > 10)
			throw new InvalidEvaluationException(notaDoUsuario);
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
	
	public void setNomeDoUsuario(String nomeDoUsuario) throws EmptyFieldException {
		if(nomeDoUsuario.isBlank() || nomeDoUsuario.isEmpty())
			throw new EmptyFieldException("Nome do usuário");
		this.nomeDoUsuario = nomeDoUsuario;
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
				+ "\n, Nota = " + notaDoUsuario;
	}
	
}
