package br.com.ufape.poo.brenchbook.exception.comunicacao;

/**
 * Exceção Geral de Classes Básicas de Negócio
 * @author Pedro_Augusto
 *
 */
public class EmptyFieldException extends Exception {
	private static final long serialVersionUID = 3805160429469022544L;
	private String campo;
	
	public EmptyFieldException(String campo) {
		super("ERRO: o conteúdo do "+campo+" está vazio!\n");
		System.out.println(this.getMessage());
		this.campo = campo;
	}
	
	public String getCampo() { return campo; }
}
