package br.com.ufape.poo.brenchbook.model.biblioteca;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import br.com.ufape.poo.brenchbook.model.livro.Livro;

/**
 * Classe Básica de Negócio - Biblioteca (Objeto de Contenção de Livros cujo é divida em dois segmentos...)
 * @author Diogo_Silva & Max_David
 *
 */
@Entity(name = "Biblioteca")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo_de_biblioteca")
public abstract class Biblioteca {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_biblioteca")
	private long id;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_biblioteca")
	private List<Livro> livros;
	
	public Biblioteca() {
		this.livros = new ArrayList<Livro>();
	}
	
	public void adicionarLivro(Livro livro) {
		this.livros.add(livro);
	}
	
	public Livro retornarLivroPorIndice(int indice) {
		return livros.get(indice);
	}
	
	public void removerLivroPorIndice(int indice) {
		livros.remove(indice);
	}

	public List<Livro> getLivros() {
		return livros;
	}
	
}
