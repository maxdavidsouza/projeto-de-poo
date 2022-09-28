package br.com.ufape.poo.brenchbook.exception.login;

/**
 * Exceção de Senha Inválida
 * @author Max
 *
 */
public class InvalidPasswordException extends Exception {
	private static final long serialVersionUID = 1352903775343652990L;
	private String senha;
	
	public InvalidPasswordException(String senha) {
		super("ERRO: Senha inserida inválida!\n");
		System.out.println(this.getMessage());
	}
	
	public String getSenha() { return senha; }
}
