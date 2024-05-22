package com.upe.br.acheie.dominio.dto;

import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.dominio.utils.enums.Curso;
import com.upe.br.acheie.dominio.utils.enums.Periodo;

public record UsuarioDto(String nome, String sobrenome, Curso curso, 
		Periodo periodo, String telefone, byte[] foto) {

	public UsuarioDto(Usuario usuario) {
		this(usuario.getNome(), usuario.getSobrenome(), usuario.getCurso(), 
				usuario.getPeriodo(), usuario.getTelefone(), usuario.getFoto());
	}
	
}
