package com.upe.br.acheie.domain.dto;

import com.upe.br.acheie.domain.model.Usuario;
import com.upe.br.acheie.domain.enums.Curso;
import com.upe.br.acheie.domain.enums.Periodo;
import java.util.UUID;

public record UsuarioDto(UUID idUsuario, String nome, String sobrenome, Curso curso,
												 Periodo periodo, String telefone, byte[] foto) {

	public UsuarioDto(Usuario usuario) {
		this(usuario.getId(), usuario.getNome(), usuario.getSobrenome(), usuario.getCurso(),
				usuario.getPeriodo(), usuario.getTelefone(), usuario.getFoto());
	}
	
}
