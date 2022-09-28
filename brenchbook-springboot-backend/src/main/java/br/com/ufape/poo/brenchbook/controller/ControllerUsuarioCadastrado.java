package br.com.ufape.poo.brenchbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import br.com.ufape.poo.brenchbook.repository.RepositorioMensagem;
import br.com.ufape.poo.brenchbook.repository.RepositorioUsuarioCadastrado;
import br.com.ufape.poo.brenchbook.exception.ResourceNotFoundException;
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
	 */
	@PostMapping("/usuarios-cadastrados")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioCadastrado salvarUsuarioCadastrado(@RequestBody UsuarioCadastrado usuarioCadastrado) {
		return repositorioUsuarioCadastrado.save(usuarioCadastrado);
	}
	
	/**
	 * Recolhe os Dados de um Usuário {id}
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
	 * Atualiza dados do Usuário {id}
	 * @param id
	 * @param biografia
	 * @return
	 */
	@PutMapping("/usuarios-cadastrados/{id}/atualizar-dados")
	public ResponseEntity<UsuarioCadastrado> atualizarBiografiaDeUsuarioCadastrado(@PathVariable Long id, @RequestBody String biografia){
		UsuarioCadastrado usuarioCadastrado = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		usuarioCadastrado.setBiografia(biografia);
		UsuarioCadastrado usuarioAtualizado = repositorioUsuarioCadastrado.save(usuarioCadastrado);
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
	 */
	@PutMapping("/usuarios-cadastrados/{id}/mensagens/enviar-mensagem/{id2}")
	public ResponseEntity<UsuarioCadastrado> enviarMensagem(@PathVariable Long id, @PathVariable Long id2, @RequestBody Mensagem mensagem){
		UsuarioCadastrado remetente = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		
		UsuarioCadastrado destinatario = repositorioUsuarioCadastrado.findById(id2)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id2+"] não encontrado!"));

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
				.orElseThrow(() -> new ResourceNotFoundException("Livro ["+id+"] não encontrado!"));
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
	
	
	
	/* #AVISO: oferece problemas de remoção em cascata
	@DeleteMapping("/usuarios-cadastrados/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable long id) {
		UsuarioCadastrado usuario = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Livro ["+id+"] não encontrado!"));
		repositorioUsuarioCadastrado.delete(usuario);
	}
	*/
}
