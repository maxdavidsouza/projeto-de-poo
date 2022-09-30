package br.com.ufape.poo.brenchbook.model.livro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ufape.poo.brenchbook.exception.comunicacao.EmptyFieldException;

/**
 * Classe Básica de Negócio - Livro (Objeto de Principal Interação para/com o Sistema)
 * @author Max_David
 *
 */
@Entity(name = "Livro")
@Table(name = "livro")
public class Livro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_livro")
	private long id;
	
	@Column(name = "estado_de_publicacao")
	private String estadoDePublicacao; //Privado/Público/Bloqueada
	
	@Column(name = "estado_de_escrita")
	private String estadoDeEscrita; //Finalizado/Incompleto
	
	@Column(name = "data_de_postagem")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDePostagem;
	
	@Column(name = "data_de_edicao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDeEdicao;
	
	@Column(name = "autor", nullable = false)
	private String autor;
	
	@Column(name = "resenha", columnDefinition = "TEXT", nullable = false)
	private String resenha;
	
	@Column(name = "categoria", nullable = false)
	private String categoria;
	
	@Column(name = "titulo", nullable = false)
	private String titulo;
	
	@Column(name = "nota")
	private float nota;
	
	@Column(nullable = true)
    private String capa;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_livro")
	private List<Capitulo> capitulos; //um livro recebe uma lista de capítulos
	
	public Livro() {
		this.estadoDePublicacao = "Privado";
		this.estadoDeEscrita = "Aberto";
		this.dataDeEdicao = new Date();
		capitulos = new ArrayList<Capitulo>();
	}

	public Livro(String titulo, String resenha, String categoria) throws EmptyFieldException {
		if(titulo.isBlank() || titulo.isEmpty())
			throw new EmptyFieldException("Titulo do Livro");
		if(resenha.isBlank() || resenha.isEmpty())
			throw new EmptyFieldException("Resenha do Livro");
		if(categoria.isBlank() || categoria.isEmpty())
			throw new EmptyFieldException("Categoria do Livro");
		this.estadoDePublicacao = "Privado";
		this.estadoDeEscrita = "Aberto";
		this.dataDeEdicao = new Date();
		this.resenha = resenha;
		this.categoria = categoria;
		this.titulo = titulo;
		this.nota = 0;
		capitulos = new ArrayList<Capitulo>();
	}
	
	/**
	 * Método que adiciona um capítulo ao livro
	 * @param capitulo
	 */
	public void adicionarCapitulo(Capitulo capitulo) {
		capitulos.add(capitulo);
	}

	/**
	 * Método que gera a nota de um livro baseado na pontuação de todos seus Capítulos
	 */
	public void gerarNota() {
		float nota = 0;
		for(int i = 0; i < capitulos.size(); i++) {
			nota =+ capitulos.get(i).getPontuacao();
		}
		this.nota = nota;
	}
	
	public List<Capitulo> getCapitulos() {
		return capitulos;
	}

	public void setCapitulos(List<Capitulo> capitulos) {
		this.capitulos = capitulos;
	}
	
	public long getId() {
		return id;
	}

	public String getCapa() {
		return capa;
	}
	
	public void setCapa(String capa) {
		this.capa = capa;
	}

	public String getEstadoDePublicacao() {
		return estadoDePublicacao;
	}

	public void setEstadoDePublicacao(String estadoDePublicacao) {
		this.estadoDePublicacao = estadoDePublicacao;
	}

	public String getEstadoDeEscrita() {
		return estadoDeEscrita;
	}

	public void setEstadoDeEscrita(String estadoDeEscrita) {
		this.estadoDeEscrita = estadoDeEscrita;
	}

	public Date getDataDePostagem() {
		return dataDePostagem;
	}

	public void setDataDePostagem(Date dataDePostagem) {
		this.dataDePostagem = dataDePostagem;
	}

	public Date getDataDeEdicao() {
		return dataDeEdicao;
	}

	public void setDataDeEdicao(Date dataDeEdicao) {
		this.dataDeEdicao = dataDeEdicao;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getResenha() {
		return resenha;
	}

	public void setResenha(String resenha) {
		this.resenha = resenha;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}
	
	/**
	 * Método de Impressão de Dados para Debug de Código
	 */
	@Override
	public String toString() {
		return "Livro [id: " + id + "]{\n, Titulo = " + titulo + "\n, Resenha = " + resenha + "\n, Nota = " + nota 
				+ "\n, Autor = " + autor + "\n, Categoria = " + categoria + "\n}";
	}
	
}
