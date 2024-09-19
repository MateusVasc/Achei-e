package com.upe.br.acheie.domain.dto;

import com.upe.br.acheie.domain.enums.Category;
import com.upe.br.acheie.domain.enums.Status;
import java.time.LocalDate;

import com.upe.br.acheie.domain.entities.Item;

public record ItemDto(Status status, Category category, String description,
											String title, LocalDate lostAt, byte[] photo) {
	
	public ItemDto(Item item) {
		this(item.getStatus(), item.getCategory(), item.getDescription(),
				item.getTitle(), item.getLostAt(), item.getPhoto());
	}

}
