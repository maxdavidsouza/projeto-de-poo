package br.com.ufape.poo.brenchbook.exception.comunicacao;

/**
 * Exceção de Avaliação
 * @author Diogo_Silva
 *
 */
public class InvalidEvaluationException extends Exception {

	private static final long serialVersionUID = -6471343214315621968L;
	private int nota;
	
	public InvalidEvaluationException(int nota) {
		super("ERRO: Nota dada - " + nota + " é inválida! (acima de 10 ou abaixo de zero)");
		this.nota = nota;
	}
	
	public int getNota() { return nota; }
	
	
}
