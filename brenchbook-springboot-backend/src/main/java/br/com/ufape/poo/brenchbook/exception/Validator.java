package br.com.ufape.poo.brenchbook.exception;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Interface feita para uso de Métodos de Validação em Todos os Procedimentos do Sistema
 * @author Max_David, Pedro_Augusto & Diogo_Silva
 *
 */
public interface Validator {
	public static final char[] listaCaractereInvalido = {
		'/','-',' ','|','+','-','$',']','[','~',':',';','!','#','%','¨','&','*','(',')','§','\\','?','°','¢','£','³','²','¹','.'
	};
	
	public static final int idadeMinima = 13;
	
	//Partição de Funções de Validação de Strings
	private static boolean isCaracterValido(char caractere) {
		for(int i = 0; i < listaCaractereInvalido.length; i++) {
			if(caractere == listaCaractereInvalido[i])
				return false;
		}
		return true;
	}
	
	public static boolean isUsuarioValido(String string) {
		for(int i = 0; i < string.length(); i++) {
			if(!isCaracterValido(string.charAt(i)))
				return false;
		}
		return true;
	}
	
	public static boolean isSenhaValida(String senha) {
		// Senha de 8 a 16 caracteres com pelo menos um dígito, pelo menos um
	    // letra minúscula, pelo menos uma letra maiúscula, pelo menos uma
	    // caractere especial, e sem espaços em branco.
		final String RegrasDeSenha = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";
		final Pattern PadraoDeSenha = Pattern.compile(RegrasDeSenha);
		if(PadraoDeSenha.matcher(senha).matches())
			return true;
		return false;
	}
	
	public static boolean isEmailValido(String email) {
	    boolean isEmailIdValid = false;
	    if (email != null && email.length() > 0) {
	        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	        Matcher matcher = pattern.matcher(email);
	        if (matcher.matches()) {
	            isEmailIdValid = true;
	        }
	    }
	    return isEmailIdValid;
	}
	
	public static boolean isIdadeValida(Date dataDeNascimento) {
		Date dataAtual = new Date();
		LocalDate dataAtualConvertida = dataAtual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dataDeNascimentoConvertida = dataDeNascimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long diferenca = ChronoUnit.YEARS.between(dataDeNascimentoConvertida, dataAtualConvertida);
		if(diferenca > 13 && diferenca < 99)
			return true;
		return false;
	}
}
