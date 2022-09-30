package br.com.ufape.poo.brenchbook.junit.login;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.ufape.poo.brenchbook.exception.login.InvalidEmailException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidPasswordException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidUsernameException;
import br.com.ufape.poo.brenchbook.model.login.Login;

/**
 * Coleção de Testes da Classe Login
 * @author Pedro_Augusto
 *
 */
class LoginTest {

	/**
	 * Teste Unitário que usa a Biblioteca Faker para testar a validação de Campos da Classe Login
	 * @throws InvalidUsernameException
	 * @throws InvalidPasswordException
	 * @throws InvalidEmailException
	 */
	@Test
	public void testeValidacaoDeCampos() throws InvalidUsernameException, InvalidPasswordException, InvalidEmailException {
		Faker faker = new Faker(); //objeto responsável por gerar dados aleatórios parametrizados a cada novo teste executado
		String usuario, senha, email;
		usuario = faker.name().firstName(); //restrições de usuário: sequencial + sem caracteres numéricos ou speciais
		senha = faker.internet().password() + "F$"; //restrições de senha: sequencial + 8 à 16 caracteres + 1 min + 1 mai + 1 sp
		email = faker.internet().emailAddress(); //restrição de email padrão xxx@yyy.zzz
		//Aqui abaixo estão alguns valores invalidos para teste, basta retirar as '//' dos comentários e executar o teste.
		//usuario = "pedroca 208";
		//senha = "123"; 
		//email = "pedrosaveirodiego©outlook.com.br";
		System.out.println(usuario);
		System.out.println(senha);
		System.out.println(email);
		Login login = new Login(usuario, senha, email);
		//O esperado para este teste é que, em caso de inserção correta de dados, o objeto seja instanciado e descrito no terminal.
		System.out.println(login.toString());
	}

}
