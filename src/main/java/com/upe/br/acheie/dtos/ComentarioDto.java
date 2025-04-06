package com.upe.br.acheie.dtos;

import java.time.LocalDate;

import com.upe.br.acheie.domain.models.Comment;

public record ComentarioDto(String assunto, UsuarioDto usuario, LocalDate dataCriacao, 
		LocalDate dataRemocao) {

	public ComentarioDto(Comment comment) {
		this(comment.getSubject(), new UsuarioDto(comment.getUser()),
				comment.getCreatedAt(), comment.getDeletedAt());
	}
}
