package com.upe.br.acheie.domain.dto;

import java.time.LocalDate;

import com.upe.br.acheie.domain.entities.Comment;

public record CommentDto(String subject, UserDto user, LocalDate createdAt,
												 LocalDate removedAt) {

	public CommentDto(Comment comment) {
		this(comment.getSubject(), new UserDto(comment.getUser()),
				comment.getCreatedAt(), comment.getRemovedAt());
	}
}
