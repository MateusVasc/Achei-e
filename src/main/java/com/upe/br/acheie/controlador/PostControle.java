package com.upe.br.acheie.controlador;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upe.br.acheie.dominio.dto.ComentarioDto;
import com.upe.br.acheie.dominio.dto.ErroDto;
import com.upe.br.acheie.dominio.dto.PostDto;
import com.upe.br.acheie.dominio.servico.ComentarioServico;
import com.upe.br.acheie.dominio.servico.PostServico;
import com.upe.br.acheie.dominio.utils.enums.Cadastro;

@RestController
public class PostControle {

	@Autowired
	private PostServico postServico;
	
	@Autowired
	private ComentarioServico comentarioServico;
	
	private static final Logger log = LogManager.getLogger(PostControle.class);
	
	@PostMapping("/novo-post/{usuarioId}")
	public ResponseEntity cadastrarPost(@PathVariable UUID usuarioId, 
			@RequestBody PostDto postDto) {
		try {
			this.postServico.cadastrarPost(usuarioId, postDto);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(this.tratarErro(e));
		}
	}
	
	@GetMapping("/post/{id}")
	public ResponseEntity buscarPostPorId(@PathVariable UUID id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(postServico.buscarPostEspecifico(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.tratarErro(e));
		}
	}
	
	@GetMapping("/posts")
	public ResponseEntity buscarPosts() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(postServico.buscarPosts());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.tratarErro(e));
		}
	}
	
	@PostMapping("/post/{postId}")
	public ResponseEntity cadastrarComentario(@PathVariable UUID postId, 
			@RequestParam("usuarioId") UUID usuarioId, @RequestBody ComentarioDto comentario) {
		try {
			this.comentarioServico.cadastrarComentario(postId, usuarioId, comentario);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.tratarErro(e));
		}
	}
	
	public ErroDto tratarErro(Exception e) {
		log.error(e.getMessage(), e);
		return new ErroDto(e);
	}
}
