package com.upe.br.acheie.dominio.modelos.dto;

import com.upe.br.acheie.dominio.enums.Curso;
import com.upe.br.acheie.dominio.enums.Periodo;
import com.upe.br.acheie.dominio.modelos.Usuario;

public record UsuarioDto(String nome, String sobrenome, Curso curso, 
		Periodo periodo, String telefone, byte[] foto) {

	public UsuarioDto(Usuario usuario) {
		this(usuario.getNome(), usuario.getSobrenome(), usuario.getCurso(), 
				usuario.getPeriodo(), usuario.getTelefone(), usuario.getFoto());
	}
	
}
