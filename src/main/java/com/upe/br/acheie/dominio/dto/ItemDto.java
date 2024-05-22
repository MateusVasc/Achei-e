package com.upe.br.acheie.dominio.dto;

import java.time.LocalDate;

import com.upe.br.acheie.dominio.modelos.Item;
import com.upe.br.acheie.dominio.utils.enums.Categoria;
import com.upe.br.acheie.dominio.utils.enums.Estado;

public record ItemDto(Estado estado, Categoria categoria, String descricao, 
		String titulo, LocalDate data, byte[] foto) {
	
	public ItemDto(Item item) {
		this(item.getEstado(), item.getCategoria(), item.getDescricao(), 
				item.getTitulo(), item.getData(), item.getFoto());
	}

}
