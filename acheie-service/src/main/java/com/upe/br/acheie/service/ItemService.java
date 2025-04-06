package com.upe.br.acheie.service;

import com.upe.br.acheie.domain.enums.State;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dtos.ItemDTO;
import com.upe.br.acheie.domain.models.Item;
import com.upe.br.acheie.domain.models.Post;
import com.upe.br.acheie.repository.ItemRepository;
import com.upe.br.acheie.repository.PostRepository;
import com.upe.br.acheie.utils.AcheieException;
import com.upe.br.acheie.utils.ErrorMessages;
import com.upe.br.acheie.domain.enums.Update;

@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;
  private final PostRepository postRepository;
  private final Logger log = LogManager.getLogger(ItemService.class);

  public UUID createItem(ItemDTO itemDTO, UUID postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_POST_NOT_FOUND));

    if (itemDTO.state() == State.RETURNED) {
      throw new AcheieException(ErrorMessages.MSG_INVALID_ITEM_STATE_UPDATE);
    }

    Item item = new Item(itemDTO, post);
    itemRepository.save(item);

    return item.getId();
  }

  public Update updateItem(UUID itemId, ItemDTO itemDTO) {
    Item item = this.itemRepository.findById(itemId)
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_ITEM_NOT_FOUND));

    if (itemDTO.state() == State.RETURNED) {
      throw new AcheieException(ErrorMessages.MSG_INVALID_ITEM_STATE_UPDATE);
    }

    item.setCategory(itemDTO.category());
    item.setDate(itemDTO.date());
    item.setDescription(itemDTO.description());
    item.setTitle(itemDTO.title());
    item.setState(itemDTO.state());
    item.setImage(itemDTO.image());

    this.itemRepository.save(item);
    return Update.UPDATED_SUCCESSFULLY;
  }
}
