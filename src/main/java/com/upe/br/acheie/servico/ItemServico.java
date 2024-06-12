package com.upe.br.acheie.servico;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dominio.dto.ItemDto;
import com.upe.br.acheie.dominio.modelos.Item;
import com.upe.br.acheie.dominio.modelos.Post;
import com.upe.br.acheie.repositorio.ItemRepositorio;
import com.upe.br.acheie.repositorio.PostRepositorio;
import com.upe.br.acheie.dominio.utils.AcheieException;
import com.upe.br.acheie.dominio.utils.MensagensErro;
import com.upe.br.acheie.dominio.utils.enums.Atualizacao;

@Service
@RequiredArgsConstructor
public class ItemServico {

  private final ItemRepositorio itemRepo;

  private final PostRepositorio postRepo;

  private final Logger log = LogManager.getLogger(ItemServico.class);

  public UUID cadastrarItem(ItemDto itemDto, UUID postId) {
    Post post = postRepo.findById(postId)
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_POST_NAO_ENCONTRADO));
    Item item = new Item(itemDto, post);

    itemRepo.save(item);

    return item.getId();
  }

  public Atualizacao atualizarItem(UUID idItem, ItemDto itemDto) {
    Item item = this.itemRepo.findById(idItem)
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_ITEM_NAO_ENCONTRADO));

    item.setCategoria(itemDto.categoria());
    item.setData(itemDto.data());
    item.setDescricao(itemDto.descricao());
    item.setTitulo(itemDto.titulo());
    item.setEstado(itemDto.estado());
    item.setFoto(itemDto.foto());

    this.itemRepo.save(item);
    return Atualizacao.ATUALIZACAO_COM_SUCESSO;
  }
}
