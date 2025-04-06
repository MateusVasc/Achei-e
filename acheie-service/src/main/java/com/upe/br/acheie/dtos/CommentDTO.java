package com.upe.br.acheie.dtos;

import java.time.LocalDate;

import com.upe.br.acheie.domain.models.Comment;

public record CommentDTO(String subject, UserDTO user, LocalDate createdAt,
						 LocalDate deletedAt) {

	public CommentDTO(Comment comment) {
		this(comment.getSubject(), new UserDTO(comment.getUser()),
				comment.getCreatedAt(), comment.getDeletedAt());
	}
}
