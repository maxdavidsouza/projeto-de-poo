package br.com.ufape.poo.brenchbook.exception.livro;

public class InvalidAuthorException extends Exception {

	private static final long serialVersionUID = 796301392083695964L;
	private String autorDoLivro, nomeDoUsuario;
	
	/**
	 * Classe de Exceção para Evitar acesso de Autores à Obras de Outros Autores
	 * @param nomeDoUsuario
	 * @param autorDoLivro
	 */
	public InvalidAuthorException(String nomeDoUsuario, String autorDoLivro) {
		super("ERRO: Você não tem permissão para editar este livro, '"+nomeDoUsuario+"'!");
		this.autorDoLivro = autorDoLivro;
		this.nomeDoUsuario = nomeDoUsuario;
	}
	
	public String getAutorDoLivro() { return autorDoLivro; }
	
	public String getNomeDoUsuario() { return nomeDoUsuario; }

}
