package com.upe.br.acheie.dominio.modelos.servicos;

import java.util.UUID;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dominio.enums.Cadastro;
import com.upe.br.acheie.dominio.modelos.Comentario;
import com.upe.br.acheie.dominio.modelos.Post;
import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.dominio.modelos.dto.ComentarioDto;
import com.upe.br.acheie.dominio.modelos.repositorios.ComentarioRepositorio;
import com.upe.br.acheie.dominio.modelos.repositorios.PostRepositorio;
import com.upe.br.acheie.dominio.modelos.repositorios.UsuarioRepositorio;

@Service
public class ComentarioServico {

	@Autowired
	private ComentarioRepositorio comentarioRepo;
	
	@Autowired
	private PostRepositorio postRepo;
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	private final Logger log = LogManager.getLogger(ComentarioServico.class);

	public Cadastro comentar(UUID postId,  UUID usuarioId, ComentarioDto comentario) {
		try {
			Post post = postRepo.getReferenceById(postId);
			Usuario usuario = usuarioRepo.getReferenceById(usuarioId);
			comentarioRepo.save(new Comentario(comentario, post, usuario));
			return Cadastro.SUCESSO_CADASTRO;
		} catch (Exception e) {
			log.log(Level.ERROR, e.getMessage());;
			return Cadastro.ERRO_CADASTRO;
		}
	}
}
