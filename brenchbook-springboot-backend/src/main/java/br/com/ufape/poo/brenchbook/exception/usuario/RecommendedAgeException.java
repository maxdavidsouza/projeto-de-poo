package br.com.ufape.poo.brenchbook.exception.usuario;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Exceção de Idade Recomendada para Acessar o Sistema
 * @author Diogo_Silva & Pedro_Augusto
 *
 */
public class RecommendedAgeException extends Exception {
	private static final long serialVersionUID = -6160861929055697086L;
	private Date dataDeNascimento, dataAtual;
		
	public RecommendedAgeException(Date dataDeNascimento) {
		super("ERRO: você possui idade menor que 13 anos (idade recomendada) para fazer uso deste aplicativo.\n");
		this.dataAtual = new Date();
		this.dataDeNascimento = dataDeNascimento;
		LocalDate dataAtualConvertida = dataAtual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dataDeNascimentoConvertida = dataDeNascimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long idade = ChronoUnit.YEARS.between(dataDeNascimentoConvertida, dataAtualConvertida);
		System.out.println(this.getMessage()+"Idade calculada: "+idade+" anos");
	}
		
	public Date getDataDeNascimento() { return dataDeNascimento; }
	public Date getDataAtual() { return dataAtual; }
}
