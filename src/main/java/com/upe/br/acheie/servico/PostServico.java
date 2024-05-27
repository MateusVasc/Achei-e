package com.upe.br.acheie.servico;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dominio.dto.ComentarioDto;
import com.upe.br.acheie.dominio.dto.PostDto;
import com.upe.br.acheie.dominio.modelos.Comentario;
import com.upe.br.acheie.dominio.modelos.Item;
import com.upe.br.acheie.dominio.modelos.Post;
import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.dominio.utils.AcheieException;
import com.upe.br.acheie.dominio.utils.MensagensErro;
import com.upe.br.acheie.dominio.utils.enums.Atualizacao;
import com.upe.br.acheie.dominio.utils.enums.Cadastro;
import com.upe.br.acheie.repositorio.ItemRepositorio;
import com.upe.br.acheie.repositorio.PostRepositorio;
import com.upe.br.acheie.repositorio.UsuarioRepositorio;

@Service
public class PostServico {

	@Autowired
	private PostRepositorio postRepo;
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	@Autowired
	private ItemServico itemServico;
	
	@Autowired
	private UsuarioServico usuarioServico;
	
	@Autowired
	private ItemRepositorio itemRepo;
	
	private static final Logger log = LogManager.getLogger(PostServico.class);
	
	public Cadastro cadastrarPost(UUID usuarioID, PostDto postDto) {
		try {
			Usuario usuario =  usuarioRepo.getReferenceById(usuarioID);
			
			Post novoPost = new Post(postDto, usuario);
			postRepo.save(novoPost);
			
			UUID itemId = itemServico.cadastrarItem(postDto.item(), novoPost.getId());
			Item item = itemRepo.getReferenceById(itemId);
			novoPost.setItem(item);
			
			return Cadastro.SUCESSO_CADASTRO;
		} catch (Exception e) {
			this.tratarErros(e);
		}
		return Cadastro.ERRO_CADASTRO;
	}
	
	public PostDto buscarPostEspecifico(UUID idPost) {
		try {
			Optional<Post> post = postRepo.findById(idPost);
			if (post.isEmpty()) {
				return null;
			}
			List<ComentarioDto> comentarios = post.get().getComentarios().stream().
					map(ComentarioDto::new).toList();
			return new PostDto(post.get(), comentarios);
		} catch (Exception e) {
			this.tratarErros(e);
		}
		return null;
	}
	
	public List<PostDto> buscarPosts() {
		try { 
			return postRepo.findAll().stream().map(post -> new PostDto(post, List.of())).toList();
		} catch (Exception e) {
			this.tratarErros(e);
		}
		return List.of();
	}
	
	public Atualizacao atualizarPost(UUID postId, PostDto postDto) {
		try {
			Post post = this.postRepo.getReferenceById(postId);
			
			post.setTipo(postDto.tipo());
			post.setCriacaoDoPost(postDto.dataCriacao());
			post.setRemocaoDoPost(postDto.dataRemocao());
			this.usuarioServico.atualizarUsuario(post.getUsuario().getId(), postDto.usuario());
			this.itemServico.atualizarItem(post.getItem().getId(), postDto.item());
			
			return Atualizacao.ATUALIZACAO_COM_SUCESSO;
		} catch (Exception e) {
			this.tratarErros(e);
		}
		return Atualizacao.ATUALIZACAO_COM_FALHA;
	}
	
	
	public void tratarErros(Exception e) {
		if (e instanceof IllegalArgumentException) {
			log.error(MensagensErro.MSG_ELEMENTO_AUSENTE, e);
			throw new AcheieException(MensagensErro.MSG_ELEMENTO_AUSENTE, e);
		} else if (e instanceof OptimisticLockingFailureException) {
			log.error(MensagensErro.MSG_ERRO_INESPERADO, e);
			throw new AcheieException(MensagensErro.MSG_ERRO_INESPERADO, e);
		} else if (e instanceof NoSuchElementException) {
			log.error(MensagensErro.MSG_ERRO_OPTIONAL, e);
			throw new AcheieException(MensagensErro.MSG_ERRO_OPTIONAL, e);
		} else {
			log.error(e.getMessage(), e);
			throw new AcheieException(e.getMessage(), e);
		}
	}
}
