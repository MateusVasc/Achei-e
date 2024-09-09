package com.upe.br.acheie.domain.dto;

import java.time.LocalDate;

import com.upe.br.acheie.domain.model.Comment;

public record CommentDto(String assunto, UserDto usuario, LocalDate dataCriacao,
												 LocalDate dataRemocao) {

	public CommentDto(Comment comment) {
		this(comment.getSubject(), new UserDto(comment.getUser()),
				comment.getCreatedAt(), comment.getRemovedAt());
	}
}
