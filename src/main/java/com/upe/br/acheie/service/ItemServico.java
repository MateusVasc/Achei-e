package com.upe.br.acheie.service;

import com.upe.br.acheie.domain.enums.State;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dtos.ItemDto;
import com.upe.br.acheie.domain.models.Item;
import com.upe.br.acheie.domain.models.Post;
import com.upe.br.acheie.repository.ItemRepositorio;
import com.upe.br.acheie.repository.PostRepositorio;
import com.upe.br.acheie.utils.AcheieException;
import com.upe.br.acheie.utils.MensagensErro;
import com.upe.br.acheie.domain.enums.Update;

@Service
@RequiredArgsConstructor
public class ItemServico {

  private final ItemRepositorio itemRepo;

  private final PostRepositorio postRepo;

  private final Logger log = LogManager.getLogger(ItemServico.class);

  public UUID cadastrarItem(ItemDto itemDto, UUID postId) {
    Post post = postRepo.findById(postId)
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_POST_NAO_ENCONTRADO));

    if (itemDto.state() == State.RETURNED) {
      throw new AcheieException(MensagensErro.MSG_ATUALIZAR_ESTADO_ITEM_INVALIDO);
    }

    Item item = new Item(itemDto, post);
    itemRepo.save(item);

    return item.getId();
  }

  public Update atualizarItem(UUID idItem, ItemDto itemDto) {
    Item item = this.itemRepo.findById(idItem)
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_ITEM_NAO_ENCONTRADO));

    if (itemDto.state() == State.RETURNED) {
      throw new AcheieException(MensagensErro.MSG_ATUALIZAR_ESTADO_ITEM_INVALIDO);
    }

    item.setCategory(itemDto.category());
    item.setDate(itemDto.data());
    item.setDescription(itemDto.descricao());
    item.setTitle(itemDto.titulo());
    item.setState(itemDto.state());
    item.setImage(itemDto.foto());

    this.itemRepo.save(item);
    return Update.UPDATED_SUCESSFULLY;
  }
}
