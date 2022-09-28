package br.com.ufape.poo.brenchbook.exception.login;

/**
 * Exceção de Email Inválido
 * @author Max_David
 *
 */
public class InvalidEmailException extends Exception {
	private static final long serialVersionUID = -5006765425323967892L;
	private String email;
	
	public InvalidEmailException(String senha) {
		super("ERRO: Email inserido inválido!\n");
		System.out.println(this.getMessage());
	}
	
	public String getEmail() { return email; }
}
