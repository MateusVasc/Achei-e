package com.upe.br.acheie.domain.dto;

import com.upe.br.acheie.domain.enums.Category;
import com.upe.br.acheie.domain.enums.Status;
import java.time.LocalDate;

import com.upe.br.acheie.domain.model.Item;

public record ItemDto(Status status, Category category, String descricao,
											String titulo, LocalDate data, byte[] foto) {
	
	public ItemDto(Item item) {
		this(item.getStatus(), item.getCategory(), item.getDescription(),
				item.getTitle(), item.getLostAt(), item.getPhoto());
	}

}
