package com.upe.br.acheie.dominio.modelos.dto;

import java.util.Date;

import com.upe.br.acheie.dominio.enums.Categoria;
import com.upe.br.acheie.dominio.enums.Estado;
import com.upe.br.acheie.dominio.modelos.Item;

public record ItemDto(Estado estado, Categoria categoria, String descricao, 
		String titulo, Date data, byte[] foto) {
	
	public ItemDto(Item item) {
		this(item.getEstado(), item.getCategoria(), item.getDescricao(), 
				item.getTitulo(), item.getData(), item.getFoto());
	}

}
