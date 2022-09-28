package br.com.ufape.poo.brenchbook.model.usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ufape.poo.brenchbook.exception.Validator;
import br.com.ufape.poo.brenchbook.exception.comunicacao.EmptyFieldException;
import br.com.ufape.poo.brenchbook.exception.usuario.RecommendedAgeException;
import br.com.ufape.poo.brenchbook.model.biblioteca.BibliotecaPrivada;
import br.com.ufape.poo.brenchbook.model.comunicacao.Avaliacao;
import br.com.ufape.poo.brenchbook.model.comunicacao.Mensagem;
import br.com.ufape.poo.brenchbook.model.livro.Capitulo;
import br.com.ufape.poo.brenchbook.model.login.Login;

/**
 * Classe Básica de Negócio - Usuário com Cadastro (Usuário Comum)
 * @author Pedro_Augusto & Max_David
 *
 */
@Entity(name = "UsuarioCadastrado")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="tipo_de_usuario")
@Table(name = "usuario_cadastrado")
public class UsuarioCadastrado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "estado")
	private boolean ativo;
	
	@Column(name = "data_de_nascimento")
	@Temporal(TemporalType.DATE)
	private Date dataDeNascimento;
	
	@Column(name = "biografia", columnDefinition = "TINYTEXT")
	private String biografia;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_login")
	private Login login;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario")
	private List<Mensagem> chat;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario")
	private List<Avaliacao> avaliacoes;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_biblioteca")
	private BibliotecaPrivada bibliotecaPrivada;
	
	public UsuarioCadastrado() {
		this.ativo = true;
		this.chat = new ArrayList<Mensagem>();
		this.avaliacoes = new ArrayList<Avaliacao>();
		this.bibliotecaPrivada = new BibliotecaPrivada();
	}
	
	public UsuarioCadastrado(String nome, String dataDeNascimento, String biografia, Login login) throws ParseException, EmptyFieldException, RecommendedAgeException {
		Date dataConvertida = new SimpleDateFormat("dd/MM/yyyy").parse(dataDeNascimento);
		if(nome.isBlank() || nome.isEmpty())
			throw new EmptyFieldException("Nome");
		if(dataDeNascimento.isBlank() || dataDeNascimento.isEmpty())
			throw new EmptyFieldException("Data de Nascimento");
		if(!Validator.isIdadeValida(dataConvertida))
			throw new RecommendedAgeException(dataConvertida);
		this.nome = nome;
		this.ativo = true;
		this.dataDeNascimento = dataConvertida;
		this.biografia = biografia;
		this.login = login;
		this.chat = new ArrayList<Mensagem>();
		this.avaliacoes = new ArrayList<Avaliacao>();
		this.bibliotecaPrivada = new BibliotecaPrivada();
	}
	
	/**
	 * Método que realiza o Envio de Mensagens (User-2-User)
	 * @param mensagem
	 * @param usuarioCadastrado
	 */
	public void enviarMensagem(Mensagem mensagem, UsuarioCadastrado usuarioCadastrado) {
		this.getChat().add(mensagem);
		usuarioCadastrado.getChat().add(mensagem);
	}
	
	/**
	 * Método que realiza o Envio de Avaliações (User-2-Content)
	 * @param avaliacao
	 * @param capitulo
	 */
	public void enviarAvaliacao(Avaliacao avaliacao, Capitulo capitulo) {
		this.getAvaliacoes().add(avaliacao);
		capitulo.getAvaliacao().add(avaliacao);
	}
	
	/**
	 * Método que realiza o Envio de Advertências (User-2-User-Plus)
	 * @param mensagem
	 * @param usuarioSuporte
	 */
	public void reportarUsuario(Mensagem mensagem, UsuarioSuporte usuarioSuporte) {
		usuarioSuporte.getChat().add(mensagem);
	}
	
	public List<Avaliacao> getAvaliacoes(){
		return avaliacoes;
	}
	
	public List<Mensagem> getChat(){
		return chat;
	}
	
	public BibliotecaPrivada getBibliotecaPrivada() {
		return bibliotecaPrivada;
	}
	
	public void setLogin(Login login) {
		this.login = login;
	}
	
	public Login getLogin() {
		return login;
	}

	public String getNome() {
		return nome;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(String dataDeNascimento) throws ParseException {
		Date dataConvertida = new SimpleDateFormat("dd/MM/yyyy").parse(dataDeNascimento);
		this.dataDeNascimento = dataConvertida;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}
	
	/**
	 * Método de Impressão de Dados para Debug de Código
	 */
	@Override
	public String toString() {
		return "Usuario [id: " + id + "]{\n, Nome = " + nome + "\n, Biografia = " + biografia + 
				"\n, Data de Nascimento = " + dataDeNascimento.toString() + "\n, Ativo = " + ativo + "\n}\n" +
				"Login {\n" + "Usuario = " + login.getUsuario() + "\n, Senha = " + login.getSenha() + "\n}";
	}
}
