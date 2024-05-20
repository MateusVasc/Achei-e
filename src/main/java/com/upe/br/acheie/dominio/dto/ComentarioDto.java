package com.upe.br.acheie.dominio.dto;

import java.time.LocalDate;

import com.upe.br.acheie.dominio.modelos.Comentario;

public record ComentarioDto(String assunto, UsuarioDto usuario, LocalDate dataCriacao, 
		LocalDate dataRemocao) {

	public ComentarioDto(Comentario comentario) {
		this(comentario.getAssunto(), new UsuarioDto(comentario.getUsuario()), 
				comentario.getCriacaoDoComentario(), comentario.getRemocaoDoComentario());
	}
}
