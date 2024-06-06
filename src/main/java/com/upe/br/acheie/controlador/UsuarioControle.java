package com.upe.br.acheie.controlador;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upe.br.acheie.dominio.dto.EmailDto;
import com.upe.br.acheie.dominio.dto.ErroDto;
import com.upe.br.acheie.dominio.dto.UsuarioDto;
import com.upe.br.acheie.dominio.dto.request.LoginRequest;
import com.upe.br.acheie.dominio.utils.MensagemUtil;
import com.upe.br.acheie.dominio.utils.enums.Atualizacao;
import com.upe.br.acheie.servico.UsuarioServico;

@RestController
@RequestMapping("/achei-e")
public class UsuarioControle {
	
	@Autowired
	private UsuarioServico usuarioServico;
	
	public final Logger log = LogManager.getLogger(UsuarioControle.class);
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> buscarUsuarioPorId(@PathVariable UUID id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(usuarioServico.buscarUsuarioPorId(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tratarErro(e));
		}
	}
	
	@PutMapping("/usuario")
	public ResponseEntity<?> atualizarUsuario(@RequestParam(value="usuarioId") UUID idUsuario, @RequestBody UsuarioDto usuarioDto) {
		try {
			Atualizacao atualizarUsuario = this.usuarioServico.atualizarUsuario(idUsuario, usuarioDto);
			return ResponseEntity.status(HttpStatus.OK).body(new MensagemUtil(atualizarUsuario.getMensagem()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(this.tratarErro(e));
		}
	}
	
	@PutMapping("/usuario/nova-senha")
	public ResponseEntity<?> requisitarMudarSenha(@RequestBody EmailDto emailDto) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(new MensagemUtil(this.usuarioServico.requisitarMudarSenha(emailDto.email())));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.tratarErro(e));
		}
	}
	
	@PutMapping("usuario/redefinir-senha")
	public ResponseEntity<?> atualizarSenha(@RequestBody LoginRequest login) {
		try {
			Atualizacao atualizarSenha = this.usuarioServico.atualizarSenha(login);
			return ResponseEntity.status(HttpStatus.OK).body(new MensagemUtil(atualizarSenha.getMensagem()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.tratarErro(e));
		}
	}
	
	public ErroDto tratarErro(Exception e) {
		log.error(e.getMessage(), e);
		return new ErroDto(e);
	}

}
