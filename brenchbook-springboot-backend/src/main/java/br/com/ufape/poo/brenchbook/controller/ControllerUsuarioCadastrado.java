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

import br.com.ufape.poo.brenchbook.repository.RepositorioCapitulo;
import br.com.ufape.poo.brenchbook.repository.RepositorioLivro;
import br.com.ufape.poo.brenchbook.repository.RepositorioMensagem;
import br.com.ufape.poo.brenchbook.repository.RepositorioUsuarioCadastrado;
import br.com.ufape.poo.brenchbook.exception.ResourceNotFoundException;
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
	
	@GetMapping("/usuarios-cadastrados")
	public List<UsuarioCadastrado> listarUsuarioCadastrado(){
		return repositorioUsuarioCadastrado.findAll();
	}
	
	@PostMapping("/usuarios-cadastrados")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioCadastrado salvarUsuarioCadastrado(@RequestBody UsuarioCadastrado usuarioCadastrado) {
		return repositorioUsuarioCadastrado.save(usuarioCadastrado);
	}
	
	@GetMapping("/usuarios-cadastrados/{id}")
	@ResponseStatus(HttpStatus.FOUND)
	public ResponseEntity<UsuarioCadastrado> procurarUsuarioPorId(@PathVariable long id) {
		UsuarioCadastrado usuarioCadastrado = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		return ResponseEntity.ok(usuarioCadastrado);
	}
	
	@PutMapping("/usuarios-cadastrados/{id}/atualizar-dados")
	public ResponseEntity<UsuarioCadastrado> atualizarBiografiaDeUsuarioCadastrado(@PathVariable Long id, @RequestBody String biografia){
		UsuarioCadastrado usuarioCadastrado = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		usuarioCadastrado.setBiografia(biografia);
		UsuarioCadastrado usuarioAtualizado = repositorioUsuarioCadastrado.save(usuarioCadastrado);
		return ResponseEntity.ok(usuarioAtualizado);
	}
	
	@GetMapping("/usuarios-cadastrados/{id}/mensagens/enviadas")
	@ResponseStatus(HttpStatus.FOUND)
	public List<Mensagem> listarMensagensEnviadas(@PathVariable Long id){
		UsuarioCadastrado remetente = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		return repositorioMensagem.findByUsuarioRemetente(remetente);
	}
	
	@GetMapping("usuarios-cadastrados/{id}/mensagens/recebidas")
	@ResponseStatus(HttpStatus.FOUND)
	public List<Mensagem> listarMensagensRecebidas(@PathVariable Long id){
		UsuarioCadastrado usuario = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		return usuario.getChat();
	}
	
	@PutMapping("/usuarios-cadastrados/{id}/mensagens/enviar-mensagem/{id2}")
	public ResponseEntity<UsuarioCadastrado> enviarMensagem(@PathVariable Long id, @PathVariable Long id2, @RequestBody Mensagem mensagem){
		UsuarioCadastrado remetente = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		
		UsuarioCadastrado destinatario = repositorioUsuarioCadastrado.findById(id2)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id2+"] não encontrado!"));

		repositorioMensagem.save(mensagem);
		remetente.enviarMensagem(mensagem, destinatario);
		repositorioUsuarioCadastrado.save(remetente);
		repositorioUsuarioCadastrado.save(destinatario);
		return ResponseEntity.ok(remetente);
	}
	
	@GetMapping("/usuarios-cadastrados/{id}/livros")
	@ResponseStatus(HttpStatus.FOUND)
	public List<Livro> listarLivrosPrivados(@PathVariable Long id){
		UsuarioCadastrado usuario = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		return usuario.getBibliotecaPrivada().getLivros();
	}
	
	@PostMapping("/usuarios-cadastrados/{id}/livros")
	@ResponseStatus(HttpStatus.CREATED)
	public Livro criarLivroPrivado(@PathVariable Long id, @RequestBody Livro livro) {
		UsuarioCadastrado usuario = repositorioUsuarioCadastrado.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário ["+id+"] não encontrado!"));
		livro.setUsuario(usuario);
		usuario.getBibliotecaPrivada().adicionarLivro(livro);
		return repositorioLivro.save(livro);
	}
	
	@GetMapping("usuarios-cadastrados/{id}/livros/{id2}/capitulos")
	@ResponseStatus(HttpStatus.FOUND)
	public List<Capitulo> listarCapitulos(@PathVariable Long id, @PathVariable Long id2){
		Livro livro = repositorioLivro.findById(id2)
				.orElseThrow(() -> new ResourceNotFoundException("Livro ["+id2+"] não encontrado!"));
		return livro.getCapitulos();
	}
	
	@PostMapping("usuarios-cadastrados/{id}/livros/{id2}/capitulos")
	@ResponseStatus(HttpStatus.CREATED)
	public Capitulo criarCapitulo(@PathVariable Long id, @PathVariable Long id2, @RequestBody Capitulo capitulo) {
		Livro livro = repositorioLivro.findById(id2)
				.orElseThrow(() -> new ResourceNotFoundException("Livro ["+id2+"] não encontrado!"));
		livro.adicionarCapitulo(capitulo);
		return repositorioCapitulo.save(capitulo);
	}
	
	/*
	@DeleteMapping("/usuarios-cadastrados/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable("codigo") long codigo) {
		repositorioUsuarioCadastrado.deleteById(codigo);
	}*/
}
