package com.upe.br.acheie.dominio.modelos.dto;

import java.util.Date;

import com.upe.br.acheie.dominio.modelos.Comentario;

public record ComentarioDto(String assunto, UsuarioDto usuario, Date dataCriacao, 
		Date dataRemocao) {

	public ComentarioDto(Comentario comentario) {
		this(comentario.getAssunto(), new UsuarioDto(comentario.getUsuario()), 
				comentario.getCriacaoDoComentario(), comentario.getRemocaoDoComentario());
	}
}
