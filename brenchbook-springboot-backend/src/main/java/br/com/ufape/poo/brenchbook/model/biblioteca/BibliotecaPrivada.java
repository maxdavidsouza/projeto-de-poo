package br.com.ufape.poo.brenchbook.model.biblioteca;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.ufape.poo.brenchbook.model.livro.Livro;

/**
 * Classe Básica de Negócio - Biblioteca Privada (Exclusiva para cada Usuário com Cadastro no Sistema)
 * @author Diogo_Silva
 *
 */
@Entity(name = "BibliotecaPrivada")
@Table(name = "biblioteca_privada")
public class BibliotecaPrivada extends Biblioteca {
	
	@OneToMany
	@JoinColumn(name = "id_biblioteca")
	private List<Livro> favoritos;
	
	public BibliotecaPrivada() {
		super();
		this.favoritos = new ArrayList<Livro>();
	}
	
	public void adicionarFavoritos(Livro livro) {
		favoritos.add(livro);
	}
	
	public void removerFavoritos(int indice) {
		favoritos.remove(indice);
	}
	
}
