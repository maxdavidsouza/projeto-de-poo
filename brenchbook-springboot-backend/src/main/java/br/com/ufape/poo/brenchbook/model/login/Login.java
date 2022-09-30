package br.com.ufape.poo.brenchbook.model.login;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.ufape.poo.brenchbook.exception.login.InvalidEmailException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidPasswordException;
import br.com.ufape.poo.brenchbook.exception.login.InvalidUsernameException;
import br.com.ufape.poo.brenchbook.exception.Validator;

/**
 * Classe Básica de Negócio - Login (Objeto de Autenticação para/com o Usuário)
 * @author Diogo_Silva
 *
 */
@Entity
@Table(name = "login")
public class Login {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_login")
	private long id;
	
	@Column(name = "usuario")
	private String usuario;
	
	@Column(name = "senha")
	private String senha;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "dataDeCadastro")
	private Date dataDeCadastro;
	
	public Login() {
		this.dataDeCadastro = new Date();
	}
	
	public Login(String usuario, String senha, String email) throws InvalidUsernameException, InvalidPasswordException, InvalidEmailException{
		if(usuario.isEmpty() || usuario.isBlank() || !Validator.isUsuarioValido(usuario))
			throw new InvalidUsernameException(usuario);
		if(senha.isEmpty() || senha.isBlank() || !Validator.isSenhaValida(senha))
			throw new InvalidPasswordException(senha);
		if(email.isEmpty() || email.isBlank() || !Validator.isEmailValido(email))
			throw new InvalidEmailException(email);
		this.usuario = usuario;
		this.senha = senha;
		this.email = email;
		this.dataDeCadastro = new Date();
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) throws InvalidUsernameException {
		if(usuario.isEmpty() || usuario.isBlank() || !Validator.isUsuarioValido(usuario))
			throw new InvalidUsernameException(usuario);
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) throws InvalidPasswordException {
		if(senha.isEmpty() || senha.isBlank() || !Validator.isSenhaValida(senha))
			throw new InvalidPasswordException(senha);
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws InvalidEmailException {
		if(email.isEmpty() || email.isBlank() || !Validator.isEmailValido(email))
			throw new InvalidEmailException(email);
		this.email = email;
	}

	public Date getDataDeCadastro() {
		return dataDeCadastro;
	}
	
	/**
	 * Método de Impressão de Dados para Debug de Código
	 */
	@Override
	public String toString() {
		return "Login [id: " + id + "]{\n, Usuario = " + usuario + "\n, Senha = " + senha + "\n, Email = " + email + "\n}";
	}
}
