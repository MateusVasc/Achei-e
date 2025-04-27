package com.upe.br.acheie.services;

import com.upe.br.acheie.domain.entities.Item;
import com.upe.br.acheie.dtos.response.PageResponse;
import com.upe.br.acheie.mappers.ToItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.repositories.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;

  public PageResponse getAllItems(Pageable pageable) {
    Page<Item> itens = this.itemRepository.findAll(pageable);

    return new PageResponse(
            ToItemDTO.toItemDTOs(itens.toList()),
            itens.getPageable().getPageNumber(),
            itens.getPageable().getPageSize(),
            itens.getTotalElements(),
            itens.getTotalPages()
    );
  }
}
