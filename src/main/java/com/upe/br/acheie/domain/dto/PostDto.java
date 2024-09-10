package com.upe.br.acheie.domain.dto;

import com.upe.br.acheie.domain.enums.Type;
import java.time.LocalDate;
import java.util.List;

import com.upe.br.acheie.domain.entities.Post;
import java.util.UUID;

public record PostDto(UUID postId, Type type, LocalDate createdAt, LocalDate removedAt,
											UserDto user, ItemDto item, List<CommentDto> comments) {

	public PostDto(Post post) {
		this(post.getId(), post.getType(), post.getCreatedAt(), post.getRemovedAt(),
				new UserDto(post.getUser()), new ItemDto(post.getItem()),
				post.getComments().stream().map(CommentDto::new).toList());
	}

}
