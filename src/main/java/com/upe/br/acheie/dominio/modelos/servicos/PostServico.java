package com.upe.br.acheie.dominio.modelos.servicos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dominio.enums.Cadastro;
import com.upe.br.acheie.dominio.modelos.Item;
import com.upe.br.acheie.dominio.modelos.Post;
import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.dominio.modelos.dto.ComentarioDto;
import com.upe.br.acheie.dominio.modelos.dto.PostDto;
import com.upe.br.acheie.dominio.modelos.repositorios.ItemRepositorio;
import com.upe.br.acheie.dominio.modelos.repositorios.PostRepositorio;
import com.upe.br.acheie.dominio.modelos.repositorios.UsuarioRepositorio;

@Service
public class PostServico {

	@Autowired
	private PostRepositorio postRepo;
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	@Autowired
	private ItemServico itemServico;
	
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
			log.log(Level.ERROR, e.getMessage());
			return Cadastro.ERRO_CADASTRO;
		}
	}
	
	public PostDto buscarPostEspecifico(UUID idPost) {
		try {
			Optional<Post> post = postRepo.findById(idPost);
			if (post.isEmpty()) {
				log.log(Level.ERROR, "Não há elemento dentro do Optional");
				throw new RuntimeException();
			}
			List<ComentarioDto> comentarios = post.get().getComentarios().stream().
					map(ComentarioDto::new).toList();
			PostDto postDto = new PostDto(post.get(), comentarios);
			return postDto;
		} catch (Exception e) {
			log.log(Level.ERROR, e.getMessage());
			return null;
		}
	}
	
	public List<PostDto> buscarPosts() {
		try {
			List<PostDto> posts = postRepo.findAll().stream().map(post -> new PostDto(post, List.of())).toList();
			return posts;
		} catch (Exception e) {
			log.log(Level.ERROR, "Erro ao acessar o feed do usuário");
			return List.of();
		}
	}
}
