package br.com.ufape.poo.brenchbook.exception.login;

/**
 * Exceção de Nome de Usuário Inválido
 * @author Max
 *
 */
public class InvalidUsernameException extends Exception {
	private static final long serialVersionUID = -2979809373611288856L;
	private String usuario;
	
	public InvalidUsernameException(String usuario) {
		super("ERRO: Nome de Usuário inválido!\n");
		this.usuario = usuario;
		System.out.println(this.getMessage());
	}
	
	public String getUsuario() { return usuario; }
}
