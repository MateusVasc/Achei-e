package com.upe.br.acheie.domain.dto;

import java.time.LocalDate;

import com.upe.br.acheie.domain.model.Comentario;

public record ComentarioDto(String assunto, UsuarioDto usuario, LocalDate dataCriacao, 
		LocalDate dataRemocao) {

	public ComentarioDto(Comentario comentario) {
		this(comentario.getAssunto(), new UsuarioDto(comentario.getUsuario()), 
				comentario.getCriacaoDoComentario(), comentario.getRemocaoDoComentario());
	}
}
