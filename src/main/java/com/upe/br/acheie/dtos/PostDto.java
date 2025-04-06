package com.upe.br.acheie.dtos;

import java.time.LocalDate;
import java.util.List;

import com.upe.br.acheie.domain.enums.Type;
import com.upe.br.acheie.domain.models.Post;

import java.util.UUID;

public record PostDto(UUID idPost, Type type, LocalDate dataCriacao, LocalDate dataRemocao,
                      UsuarioDto usuario, ItemDto item, List<ComentarioDto> comentarios) {

	public PostDto(Post post) {
		this(post.getId(), post.getType(), post.getCreatedAt(), post.getDeletedAt(),
				new UsuarioDto(post.getUser()), new ItemDto(post.getItem()),
				post.getComments().stream().map(ComentarioDto::new).toList());
	}

}
