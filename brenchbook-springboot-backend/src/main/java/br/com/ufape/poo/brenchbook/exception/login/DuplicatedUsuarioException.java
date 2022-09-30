package br.com.ufape.poo.brenchbook.exception.login;

public class DuplicatedUsuarioException extends Exception {
	private static final long serialVersionUID = -534521219320771821L;
	private String nomeDeUsuario;
	
	public DuplicatedUsuarioException(String nomeDeUsuario) {
		super("ERRO: nome de usuário indisponível.\n");
		this.nomeDeUsuario = nomeDeUsuario;
		System.out.println(this.getMessage()+"Nome de usuário: "+nomeDeUsuario+" já foi inserido no sistema!");
	}
	
	public String getNomeDeUsuario() { return nomeDeUsuario; }

}
