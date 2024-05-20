package com.upe.br.acheie.dominio.servicos;

import java.util.UUID;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dominio.dto.ComentarioDto;
import com.upe.br.acheie.dominio.modelos.Comentario;
import com.upe.br.acheie.dominio.modelos.Post;
import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.dominio.repositorios.ComentarioRepositorio;
import com.upe.br.acheie.dominio.repositorios.PostRepositorio;
import com.upe.br.acheie.dominio.repositorios.UsuarioRepositorio;
import com.upe.br.acheie.dominio.utils.AcheieException;
import com.upe.br.acheie.dominio.utils.MensagensErro;
import com.upe.br.acheie.dominio.utils.enums.Cadastro;

@Service
public class ComentarioServico {

	@Autowired
	private ComentarioRepositorio comentarioRepo;
	
	@Autowired
	private PostRepositorio postRepo;
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	private final Logger log = LogManager.getLogger(ComentarioServico.class);

	public Cadastro cadastrarComentario(UUID postId,  UUID usuarioId, ComentarioDto comentario) {
		try {
			Post post = postRepo.getReferenceById(postId);
			Usuario usuario = usuarioRepo.getReferenceById(usuarioId);
			comentarioRepo.save(new Comentario(comentario, post, usuario));
			return Cadastro.SUCESSO_CADASTRO;
		} catch (Exception e) {
			this.tratarErros(e);
		}
		return Cadastro.ERRO_CADASTRO;
	}
	
	public void tratarErros(Exception e) {
		if (e instanceof IllegalArgumentException) {
			log.error(MensagensErro.MSG_ELEMENTO_AUSENTE, e);
			throw new AcheieException(MensagensErro.MSG_ELEMENTO_AUSENTE, e);
		} else if (e instanceof OptimisticLockingFailureException) {
			log.error(MensagensErro.MSG_ERRO_INESPERADO, e);
			throw new AcheieException(MensagensErro.MSG_ERRO_INESPERADO, e);
		} else {
			log.error(e.getMessage(), e);
			throw new AcheieException(e.getMessage(), e);
		}
	}
}
