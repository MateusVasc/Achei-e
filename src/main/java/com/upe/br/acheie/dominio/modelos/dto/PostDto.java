package com.upe.br.acheie.dominio.modelos.dto;

import java.util.Date;
import java.util.List;

import com.upe.br.acheie.dominio.enums.Tipo;
import com.upe.br.acheie.dominio.modelos.Post;

public record PostDto(Tipo tipo, Date dataCriacao, Date dataRemocao, 
		UsuarioDto usuario, ItemDto item, List<ComentarioDto> comentarios) {
	
	public PostDto(Post post, List<ComentarioDto> comentarios) {
		this(post.getTipo(), post.getCriacaoDoPost(), post.getRemocaoDoPost(), 
				new UsuarioDto(post.getUsuario()), new ItemDto(post.getItem()), 
				comentarios);
	}

}
