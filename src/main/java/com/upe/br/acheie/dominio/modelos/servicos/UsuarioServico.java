package com.upe.br.acheie.dominio.modelos.servicos;

import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.dominio.modelos.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServico {
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	private static final Logger log = LogManager.getLogger("usuario-servico");
	
	public Usuario buscarUsuarioPorId(UUID idUsuario) { //fazer try-catch
		try {
			Optional<Usuario> usuario = usuarioRepo.findById(idUsuario);
			return usuario.orElse(null);
		} catch (Exception e) {
			log.log(Level.ERROR, "Usuário não encontrado");
			return null;
		}
	}
	
}
