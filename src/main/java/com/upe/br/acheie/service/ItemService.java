package com.upe.br.acheie.service;

import com.upe.br.acheie.domain.enums.Status;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.domain.dto.ItemDto;
import com.upe.br.acheie.domain.model.Item;
import com.upe.br.acheie.domain.model.Post;
import com.upe.br.acheie.repository.ItemRepository;
import com.upe.br.acheie.repository.PostRepositorio;
import com.upe.br.acheie.domain.exceptions.AcheieException;
import com.upe.br.acheie.domain.exceptions.ErrorMessage;
import com.upe.br.acheie.domain.enums.Atualizacao;

@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepo;

  private final PostRepositorio postRepo;

  private final Logger log = LogManager.getLogger(ItemService.class);

  public UUID cadastrarItem(ItemDto itemDto, UUID postId) {
    Post post = postRepo.findById(postId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_POST_NAO_ENCONTRADO));

    if (itemDto.status() == Status.DEVOLVIDO) {
      throw new AcheieException(ErrorMessage.MSG_ATUALIZAR_ESTADO_ITEM_INVALIDO);
    }

    Item item = new Item(itemDto, post);
    itemRepo.save(item);

    return item.getId();
  }

  public Atualizacao atualizarItem(UUID idItem, ItemDto itemDto) {
    Item item = this.itemRepo.findById(idItem)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_ITEM_NAO_ENCONTRADO));

    if (itemDto.status() == Status.DEVOLVIDO) {
      throw new AcheieException(ErrorMessage.MSG_ATUALIZAR_ESTADO_ITEM_INVALIDO);
    }

    item.setCategory(itemDto.category());
    item.setLostAt(itemDto.data());
    item.setDescription(itemDto.descricao());
    item.setTitle(itemDto.titulo());
    item.setStatus(itemDto.status());
    item.setPhoto(itemDto.foto());

    this.itemRepo.save(item);
    return Atualizacao.ATUALIZACAO_COM_SUCESSO;
  }
}
