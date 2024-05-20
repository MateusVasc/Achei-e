package com.upe.br.acheie.dominio.servicos;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dominio.dto.UsuarioDto;
import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.dominio.repositorios.UsuarioRepositorio;
import com.upe.br.acheie.dominio.utils.AcheieException;
import com.upe.br.acheie.dominio.utils.MensagensErro;

@Service
public class UsuarioServico {
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	private static final Logger log = LogManager.getLogger(UsuarioServico.class);
	
	public UsuarioDto buscarUsuarioPorId(UUID idUsuario) { 
		try {
			Optional<Usuario> usuario = usuarioRepo.findById(idUsuario);
			return new UsuarioDto(usuario.get());
		} catch (Exception e) {
			this.tratarErro(e);
		}
		return null;
	}
	
	public void tratarErro(Exception e) {
		if (e instanceof IllegalArgumentException) {
			log.error(MensagensErro.MSG_ID_NULO, e);
			throw new AcheieException(MensagensErro.MSG_ID_NULO, e);
		} else if (e instanceof NoSuchElementException) {
			log.error(MensagensErro.MSG_ERRO_OPTIONAL, e);
			throw new AcheieException(MensagensErro.MSG_ERRO_OPTIONAL, e);
		} else {
			log.error(e.getMessage(), e);
			throw new AcheieException(e.getMessage());
		}
	}
	
}
