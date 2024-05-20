package com.upe.br.acheie.controles;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.upe.br.acheie.dominio.dto.ErroDto;
import com.upe.br.acheie.dominio.servicos.UsuarioServico;

@RestController
public class UsuarioControle {
	
	@Autowired
	private UsuarioServico usuarioServico;
	
	public final Logger log = LogManager.getLogger(UsuarioControle.class);
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity buscarUsuarioPorId(@PathVariable UUID id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(usuarioServico.buscarUsuarioPorId(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tratarErro(e));
		}
	}
	
	public ErroDto tratarErro(Exception e) {
		log.error(e.getMessage(), e);
		return new ErroDto(e);
	}

}
