package com.upe.br.acheie.domain.dto;

import com.upe.br.acheie.domain.enums.Type;
import java.time.LocalDate;
import java.util.List;

import com.upe.br.acheie.domain.model.Post;
import java.util.UUID;

public record PostDto(UUID idPost, Type type, LocalDate dataCriacao, LocalDate dataRemocao,
											UserDto usuario, ItemDto item, List<CommentDto> comentarios) {

	public PostDto(Post post) {
		this(post.getId(), post.getType(), post.getCreatedAt(), post.getRemovedAt(),
				new UserDto(post.getUser()), new ItemDto(post.getItem()),
				post.getComments().stream().map(CommentDto::new).toList());
	}

}
