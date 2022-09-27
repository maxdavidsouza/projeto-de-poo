package br.com.ufape.poo.brenchbook.model.biblioteca;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.ufape.poo.brenchbook.model.livro.Livro;

/**
 * Classe Básica de Negócio - Biblioteca Pública (Objeto de Alta interação no Sistema, pois à partir dela, usuários cadastrados
 * e não cadastrados poderão realizar o acesso à leitura de livros criados por usuários cadastrados)
 * @author Diogo_Silva
 *
 */
@Entity(name = "BibliotecaPublica")
@Table(name = "biblioteca_publica")
public class BibliotecaPublica extends Biblioteca {
	
	@OneToMany
	@JoinColumn(name = "id_biblioteca")
	private List<Livro> destaques;

	public BibliotecaPublica() {
		super();
		this.destaques = new ArrayList<Livro>();
	}
	
	public void trocarDestaquesPorNotaMinima(float nota) {
		List<Livro> novosDestaques = this.getLivros().stream().filter(n -> n.getNota() > nota).collect(Collectors.toList());
		this.destaques = novosDestaques;
	}
	
	public void trocarDestaquesPorNovaListaDeLivros(List<Livro> novosDestaques) {
		this.destaques = novosDestaques;
	}
}
