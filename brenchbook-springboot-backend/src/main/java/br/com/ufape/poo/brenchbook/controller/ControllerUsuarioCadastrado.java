package br.com.ufape.poo.brenchbook.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufape.poo.brenchbook.repository.RepositorioAvaliacao;
import br.com.ufape.poo.brenchbook.repository.RepositorioCapitulo;
import br.com.ufape.poo.brenchbook.repository.RepositorioLivro;
import br.com.ufape.poo.brenchbook.repository.RepositorioLogin;
import br.com.ufape.poo.brenchbook.repository.RepositorioMensagem;
import br.com.ufape.poo.brenchbook.repository.RepositorioUsuarioCadastrado;
import br.com.ufape.poo.brenchbook.exception.ResourceNotFoundException;
import br.com.ufape.poo.brenchbook.exception.comunicacao.EmptyFieldException;
import br.com.ufape.poo.brenchbook.exception.login.DuplicatedEmailException;
import br.com.ufape.poo.brenchbook.exception.login.DuplicatedUsuarioException;
import br.com.ufape.poo.brenchbook.model.comunicacao.Avaliacao;
import br.com.ufape.poo.brenchbook.model.comunicacao.Mensagem;
import br.com.ufape.poo.brenchbook.model.livro.Capitulo;
import br.com.ufape.poo.brenchbook.model.livro.Livro;
import br.com.ufape.poo.brenchbook.model.usuario.UsuarioCadastrado;
/**
 * Controlador de um Usuário Cadastrado
 * @author Pedro_Augusto
 *
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class ControllerUsuarioCadastrado {
	
	@Autowired
	private RepositorioUsuarioCadastrado repositorioUsuarioCadastrado;
	
	@Autowired
	private RepositorioMensagem repositorioMensagem;
	
	@Autowired
	private RepositorioLivro repositorioLivro;
	
	@Autowired
	private RepositorioCapitulo repositorioCapitulo;
	
	@Autowired
	private RepositorioAvaliacao repositorioAvaliacao;
	
	@Autowired
	private RepositorioLogin repositorioLogin;

	/**
	 * Lista todos os Usuários do Repositório de Usuários
	 * @return
	 */
	@GetMapping("/usuarios-cadastrados")
	public List<UsuarioCadastrado> listarUsuarioCadastrado(){
		return repositorioUsuarioCadastrado.findAll();
	}
	
	/**
	 * Insere um Novo Usuário no Repositório de Usuários
	 * @param usuarioCadastrado
	 * @return
	 * @throws DuplicatedUsuarioException 
	 * @throws DuplicatedEmailException 
	 */
	@PostMapping("/usuarios-cadastrados")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioCadastrado salvarUsuarioCadastrado(@RequestBody UsuarioCadastrado usuarioCadastrado) throws DuplicatedUsuarioException, DuplicatedEmailException {
		String usuario = usuarioCadastrado.getLogin().getUsuario();
		String email = usuarioCadastrado.getLogin().getEmail();
		if(!repositorioLogin.findByUsuario(usuario).isEmpty())
			throw new DuplicatedUsuarioException(usuarioCadastrado.getLogin().getUsuario());
		if(!repositorioLogin.findByEmail(email).isEmpty())
			throw new DuplicatedEmailException(usuarioCadastrado.getLogin().getEmail());
		return repositorioUsuarioCadastrado.save(usuarioCadastrado);
	}
	
	/**
	 * Recolhe (todos) os Dados de um Usuário {id}
	 * @param id
	 * @return
	 */
	@GetMapping("/usuarios-cadastrados/{id}")
	@ResponseStatus(HttpStatus.FOUND)
	public ResponseEntity<UsuarioCadastrado> procurarUsuarioPorId(@PathVariable long id) {
		UsuarioCadastrado usuarioCadastrado = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		return ResponseEntity.ok(usuarioCadastrado);
	}
	
	/**
	 * Atualiza dados (nome e biografia) do Usuário {id}
	 * @param id
	 * @param biografia
	 * @return
	 */
	@PutMapping("/usuarios-cadastrados/{id}/atualizar-dados")
	public ResponseEntity<UsuarioCadastrado> atualizarBiografiaDeUsuarioCadastrado(@PathVariable Long id, @RequestBody UsuarioCadastrado usuarioAtualizado){
		UsuarioCadastrado usuarioCadastrado = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		
		String nomeDoUsuarioAtualizado = usuarioAtualizado.getNome(); //definindo uma variável do tipo effectively-final

		//Realizando a atualização do Nome do Autor nos Livros a qual ele é o Dono
		List<Livro> livrosCriados = usuarioCadastrado.getBibliotecaPrivada().getLivros(); 
		if(livrosCriados != null)
			livrosCriados.forEach((x) -> {x.setAutor(nomeDoUsuarioAtualizado);});
		
		//Realizando a atualização do Nome do Remetente (SELF) nas Mensagens feitas pelo Usuário (Ainda não funcional)
		List<Mensagem> mensagensEnviadas = usuarioCadastrado.getChat()
											.stream()
											.filter(x -> x.getRemetente().equals(usuarioCadastrado.getNome()))
											.collect(Collectors.toList());
		if(mensagensEnviadas != null) {
			mensagensEnviadas.forEach((x) -> {
				try {
					/*UsuarioCadastrado usuarioReceptor = repositorioUsuarioCadastrado.findByNome(x.getDestinatario());
						int indiceDaMensagem = usuarioReceptor.getChat().indexOf(x);
						usuarioReceptor.getChat().get(indiceDaMensagem).setRemetente(nomeDoUsuarioAtualizado);
						repositorioUsuarioCadastrado.save(usuarioReceptor); (relacionamento many-to-many)*/
						x.setRemetente(nomeDoUsuarioAtualizado);
					} catch (EmptyFieldException e) {
						e.printStackTrace();
					}
			});
		}
		
		//Realizando a atualização do Nome do Destinatário nas Mensagens recebidas pelo Usuário
		List<Mensagem> mensagensRecebidas = usuarioCadastrado.getChat()
				.stream()
				.filter(x -> x.getDestinatario().equals(usuarioCadastrado.getNome()))
				.collect(Collectors.toList());
		if(mensagensRecebidas != null) {
			mensagensRecebidas.forEach((x) -> {
				try {
					x.setDestinatario(nomeDoUsuarioAtualizado);
				} catch (EmptyFieldException e) {
					e.printStackTrace();
				}
			});
		}
		
		
		//Realizando a atualização do Nome nas Avaliações feitas pelo Usuário
		List <Avaliacao> avaliacoesEnviadas = usuarioCadastrado.getAvaliacoes(); 
		if(avaliacoesEnviadas != null) {
			avaliacoesEnviadas.forEach((x) -> {
				try {
					x.setNomeDoUsuario(nomeDoUsuarioAtualizado);
				} catch (EmptyFieldException e) {
					e.printStackTrace();
				}
			});
		}
		
		//Por fim, realizando a atualização do Nome e Biografia do Usuário no seu Perfil
		usuarioCadastrado.setBiografia(usuarioAtualizado.getBiografia());
		usuarioCadastrado.setNome(nomeDoUsuarioAtualizado);
		usuarioAtualizado = repositorioUsuarioCadastrado.save(usuarioCadastrado);
		return ResponseEntity.ok(usuarioAtualizado);
	}
	
	/**
	 * Consulta todas as Mensagens do Usuário {id}
	 * @param id
	 * @return
	 */
	@GetMapping("/usuarios-cadastrados/{id}/mensagens")
	public List<Mensagem> listarMensagemPrivada(@PathVariable long id) {
		UsuarioCadastrado usuarioCadastrado = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		return usuarioCadastrado.getChat();
	}
	
	/**
	 * Envia uma Mensagem do Usuário {id} para o Usuário {id2}
	 * @param id
	 * @param id2
	 * @param mensagem
	 * @return
	 * @throws EmptyFieldException 
	 */
	@PutMapping("/usuarios-cadastrados/{id}/mensagens/enviar-mensagem/{id2}")
	public ResponseEntity<UsuarioCadastrado> enviarMensagem(@PathVariable Long id, @PathVariable Long id2, @RequestBody Mensagem mensagem) throws EmptyFieldException{
		UsuarioCadastrado remetente = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		
		UsuarioCadastrado destinatario = repositorioUsuarioCadastrado.findById(id2)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id2+"] não encontrado!"));
		mensagem.setRemetente(remetente.getNome());
		mensagem.setDestinatario(destinatario.getNome());
		remetente.enviarMensagem(mensagem, destinatario); //envia uma mensagem do remetente ao destinatario
		repositorioMensagem.save(mensagem); //salva somente uma mensagem no banco de dados
		repositorioUsuarioCadastrado.save(remetente); //salva uma cópia da mensagem no histórico do remetente
		repositorioUsuarioCadastrado.save(destinatario); //salva a mensagem no chat do destinatário
		return ResponseEntity.ok(remetente);
	}
	
	/**
	 * Consulta todos os Livros criados pelo Usuário {id}
	 * @param id
	 * @return
	 */
	@GetMapping("/usuarios-cadastrados/{id}/livros")
	public List<Livro> listarLivrosPrivados(@PathVariable Long id){
		UsuarioCadastrado usuario = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		return usuario.getBibliotecaPrivada().getLivros();
	}
	
	/**
	 * Cria um Livro dentro da Biblioteca Privada do Usuário {id}
	 * @param id
	 * @param livro
	 * @return
	 */
	@PutMapping("/usuarios-cadastrados/{id}/livros")
	public ResponseEntity<UsuarioCadastrado> criarLivro(@PathVariable Long id, @RequestBody Livro livro){
		UsuarioCadastrado usuario = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		livro.setAutor(usuario.getNome());
		usuario.getBibliotecaPrivada().adicionarLivro(livro);
		repositorioUsuarioCadastrado.save(usuario);
		return ResponseEntity.ok(usuario);
	}
	
	/**
	 * Lista todos os Capítulos de um Livro {id2} do Usuário {id}
	 * @param id
	 * @param id2
	 * @return
	 */
	@GetMapping("/usuarios-cadastrados/{id}/livros/{id2}/capitulos")
	@ResponseStatus(HttpStatus.FOUND)
	public List<Capitulo> listarCapitulos(@PathVariable Long id, @PathVariable Long id2){
		Livro livro = repositorioLivro.findById(id2)
				.orElseThrow(() -> new ResourceNotFoundException("Livro ["+id2+"] não encontrado!"));
		return livro.getCapitulos();
	}
	
	/**
	 * Cria um Capítulo para um Livro {id2} do Usuário {id}
	 * @param id
	 * @param id2
	 * @param capitulo
	 * @return
	 */
	@PutMapping("/usuarios-cadastrados/{id}/livros/{id2}/capitulos")
	public ResponseEntity<UsuarioCadastrado> criarCapitulo(@PathVariable Long id, @PathVariable Long id2, @RequestBody Capitulo capitulo) {
		UsuarioCadastrado usuario = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		Livro livro = repositorioLivro.findById(id2)
				.orElseThrow(() -> new ResourceNotFoundException("Livro ["+id2+"] não encontrado!"));
		int j = usuario.getBibliotecaPrivada().getLivros().indexOf(livro);
		usuario.getBibliotecaPrivada().getLivros().get(j).adicionarCapitulo(capitulo);
		repositorioUsuarioCadastrado.save(usuario);
		return ResponseEntity.ok(usuario);
	}
	
	/**
	 * Lista todas as Avaliações Feitas pelo Usuário {id}
	 * @param id
	 * @return
	 */
	@GetMapping("/usuarios-cadastrados/{id}/avaliacoes")
	@ResponseStatus(HttpStatus.FOUND)
	public List<Avaliacao> listarAvaliacoes(@PathVariable Long id){
		UsuarioCadastrado usuario = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		return usuario.getAvaliacoes();
	}
	
	/**
	 * Envia uma Avaliação de um Usuário {id} à um Respectivo Capítulo {id3} de um Livro {id2}
	 * @param id
	 * @param id2
	 * @param id3
	 * @param avaliacao
	 * @return
	 */
	@PutMapping("/usuarios-cadastrados/{id}/avaliacoes/enviar-avaliacao/livros/{id2}/capitulo/{id3}")
	public ResponseEntity<UsuarioCadastrado> enviarAvaliacao(@PathVariable Long id, @PathVariable Long id2, @PathVariable Long id3, @RequestBody Avaliacao avaliacao) {
		UsuarioCadastrado usuario = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Livro ["+id+"] não encontrado!"));
		Livro livro = repositorioLivro.findById(id2)
				.orElseThrow(() -> new ResourceNotFoundException("Livro ["+id+"] não encontrado!"));
		Capitulo capitulo = repositorioCapitulo.findById(id3)
				.orElseThrow(() -> new ResourceNotFoundException("Livro ["+id3+"] não encontrado!"));
		if(livro.getCapitulos().contains(capitulo)) {
			usuario.enviarAvaliacao(avaliacao, capitulo);
			repositorioAvaliacao.save(avaliacao);
			repositorioUsuarioCadastrado.save(usuario);
		}
		return ResponseEntity.ok(usuario);
	}
	
	
	/* Remoção em Cascata não Pode ser Feita devido ao Atributo Chat - ManyToMany
	@DeleteMapping("/usuarios-cadastrados/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable long id) {
		UsuarioCadastrado usuario = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		for(int i = 0; i < usuario.getBibliotecaPrivada().getLivros().size(); i++) {
			for(int j = 0; j < usuario.getBibliotecaPrivada().getLivros().get(i).getCapitulos().size(); j++) {
				repositorioCapitulo.delete(usuario.getBibliotecaPrivada().getLivros().get(i).getCapitulos().get(j));
			}
			repositorioLivro.delete(usuario.getBibliotecaPrivada().getLivros().get(i));
		}
		repositorioUsuarioCadastrado.delete(usuario);
	}
	*/
	
	@DeleteMapping("/usuarios-cadastrados/{id}/livros/{id2}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirLivro(@PathVariable long id, @PathVariable long id2) {
		UsuarioCadastrado usuario = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		Livro livro = repositorioLivro.findById(id2)
				.orElseThrow(() -> new ResourceNotFoundException("Livro ["+id2+"] não encontrado!"));
		if(usuario.getBibliotecaPrivada().getLivros().contains(livro))
			repositorioLivro.delete(livro);
	}
	
	@DeleteMapping("usuarios-cadastrados/{id}/livros/{id2}/capitulos/{id3}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirCapitulo(@PathVariable long id, @PathVariable long id2, @PathVariable long id3) {
		UsuarioCadastrado usuario = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		Livro livro = repositorioLivro.findById(id2)
				.orElseThrow(() -> new ResourceNotFoundException("Livro ["+id2+"] não encontrado!"));
		Capitulo capitulo = repositorioCapitulo.findById(id3)
				.orElseThrow(() -> new ResourceNotFoundException("Capitulo ["+id3+"] não encontrado!"));
		int i = usuario.getBibliotecaPrivada().getLivros().indexOf(livro);
		if(usuario.getBibliotecaPrivada().getLivros().get(i).getCapitulos().contains(capitulo))
			repositorioCapitulo.delete(capitulo);
	}
}
