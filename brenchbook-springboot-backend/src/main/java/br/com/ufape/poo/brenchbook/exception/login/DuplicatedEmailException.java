package br.com.ufape.poo.brenchbook.exception.login;

public class DuplicatedEmailException extends Exception {
	private static final long serialVersionUID = -4822913736083061114L;
	private String email;
	
	public DuplicatedEmailException(String email) {
		super("ERRO: este email já possui um cadastro.\n");
		this.email = email;
		System.out.println(this.getMessage()+"Email: "+email+" já foi inserido no sistema!");
	}
	
	public String getEmail() { return email; }

}
