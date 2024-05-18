package com.upe.br.acheie.dominio.modelos.servicos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dominio.modelos.Post;
import com.upe.br.acheie.dominio.modelos.repositorios.PostRepositorio;

@Service
public class PostServico {

	@Autowired
	private PostRepositorio postRepo;
	
	private static final Logger log = LogManager.getLogger("post-servico");
	
	public Post buscarPostEspecifico(UUID idPost) {
		try {
			Optional<Post> post = postRepo.findById(idPost);
			return post.orElse(null);
		} catch (Exception e) {
			log.log(Level.ERROR, "Post não encontrado");
			return null;
		}
	}
	
	public List<Post> buscarPosts() {
		try {
			return postRepo.findAll();
		} catch (Exception e) {
			log.log(Level.ERROR, "Erro ao acessar o feed do usuário");
			return List.of();
		}
	}
}
