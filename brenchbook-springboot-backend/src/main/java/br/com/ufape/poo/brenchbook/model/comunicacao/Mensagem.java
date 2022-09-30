package br.com.ufape.poo.brenchbook.model.comunicacao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ufape.poo.brenchbook.exception.comunicacao.EmptyFieldException;

/**
 * Classe Básica de Negócio - Mensagem (User-2-User)
 * @author Max_David
 *
 */
@Entity(name = "Mensagem")
@Table(name = "mensagem")
public class Mensagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_mensagem")
	private long id;
	
	@Column(name = "remetente")
	private String remetente;
	
	@Column(name = "data_de_edicao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDeEnvio;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "conteudo", columnDefinition = "TEXT")
	private String conteudo;
	
	@Column(name = "destinatario")
	private String destinatario;

	public Mensagem() {
		this.dataDeEnvio = new Date();
	}
	
	public Mensagem (String titulo, String conteudo) throws EmptyFieldException {
		if(titulo.isBlank() || titulo.isEmpty())
			throw new EmptyFieldException("titulo");
		if(conteudo.isBlank() || conteudo.isEmpty())
			throw new EmptyFieldException("conteúdo");
		this.dataDeEnvio = new Date();
		this.titulo = titulo;
		this.conteudo = conteudo;
	}
	
	/*public void setUsuarioRemetente(UsuarioCadastrado usuarioRemetente) {
		this.usuarioRemetente = usuarioRemetente;
	}*/
	
	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) throws EmptyFieldException {
		if(remetente.isBlank() || remetente.isEmpty())
			throw new EmptyFieldException("remetente");
		this.remetente = remetente;
	}

	public Date getDataDeEnvio() {
		return dataDeEnvio;
	}

	public void setDataDeEnvio(Date dataDeEnvio) {
		this.dataDeEnvio = dataDeEnvio;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) throws EmptyFieldException {
		if(titulo.isBlank() || titulo.isEmpty())
			throw new EmptyFieldException("titulo");
		this.titulo = titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) throws EmptyFieldException {
		if(conteudo.isBlank() || conteudo.isEmpty())
			throw new EmptyFieldException("conteúdo");
		this.conteudo = conteudo;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) throws EmptyFieldException {
		if(destinatario.isBlank() || destinatario.isEmpty())
			throw new EmptyFieldException("destinatário");
		this.destinatario = destinatario;
	}
	
	/**
	 * Método de Impressão de Dados para Debug de Código
	 */
	@Override
	public String toString() {
		return "Mensagem [id: " + id + "]{\n, Titulo = " + titulo + "\n, Conteúdo = " + conteudo + "\n, Remetente = " + remetente 
				+ "\n, Destinatario = " + destinatario + "\n, Data de Envio = " + dataDeEnvio.toString() + "\n}";
	}
	
}
