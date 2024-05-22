package com.upe.br.acheie.dominio.dto;

import java.time.LocalDate;
import java.util.List;

import com.upe.br.acheie.dominio.modelos.Post;
import com.upe.br.acheie.dominio.utils.enums.Tipo;

public record PostDto(Tipo tipo, LocalDate dataCriacao, LocalDate dataRemocao, 
		UsuarioDto usuario, ItemDto item, List<ComentarioDto> comentarios) {
	
	public PostDto(Post post, List<ComentarioDto> comentarios) {
		this(post.getTipo(), post.getCriacaoDoPost(), post.getRemocaoDoPost(), 
				new UsuarioDto(post.getUsuario()), new ItemDto(post.getItem()), 
				comentarios);
	}

}
