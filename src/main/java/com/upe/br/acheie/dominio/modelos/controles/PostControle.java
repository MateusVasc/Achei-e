package com.upe.br.acheie.dominio.modelos.controles;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.upe.br.acheie.dominio.modelos.servicos.PostServico;

@RestController
public class PostControle {

	@Autowired
	private PostServico postServico;
	
	@GetMapping("/post/{id}")
	public ResponseEntity buscarPostPorId(@PathVariable UUID id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(postServico.buscarPostEspecifico(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
