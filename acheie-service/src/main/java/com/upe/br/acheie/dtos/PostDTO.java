package com.upe.br.acheie.dtos;

import java.time.LocalDate;
import java.util.List;

import com.upe.br.acheie.domain.enums.Type;
import com.upe.br.acheie.domain.models.Post;

import java.util.UUID;

public record PostDTO(UUID postId, Type type, LocalDate createdAt, LocalDate deletedAt,
					  UserDTO user, ItemDTO item, List<CommentDTO> comments) {

	public PostDTO(Post post) {
		this(post.getId(), post.getType(), post.getCreatedAt(), post.getDeletedAt(),
				new UserDTO(post.getUser()), new ItemDTO(post.getItem()),
				post.getComments().stream().map(CommentDTO::new).toList());
	}

}
