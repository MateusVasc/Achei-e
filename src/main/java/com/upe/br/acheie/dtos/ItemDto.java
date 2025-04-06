package com.upe.br.acheie.dtos;

import java.time.LocalDate;

import com.upe.br.acheie.domain.enums.State;
import com.upe.br.acheie.domain.models.Item;
import com.upe.br.acheie.domain.enums.Category;

public record ItemDto(State state, Category category, String descricao,
					  String titulo, LocalDate data, byte[] foto) {
	
	public ItemDto(Item item) {
		this(item.getState(), item.getCategory(), item.getDescription(),
				item.getTitle(), item.getDate(), item.getImage());
	}

}
