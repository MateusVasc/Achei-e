package com.upe.br.acheie.dominio.modelos.servicos;

import java.util.UUID;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dominio.modelos.Item;
import com.upe.br.acheie.dominio.modelos.Post;
import com.upe.br.acheie.dominio.modelos.dto.ItemDto;
import com.upe.br.acheie.dominio.modelos.repositorios.ItemRepositorio;
import com.upe.br.acheie.dominio.modelos.repositorios.PostRepositorio;

@Service
public class ItemServico {

	@Autowired
	private ItemRepositorio itemRepo;
	
	@Autowired
	private PostRepositorio postRepo;
	
	private final Logger log = LogManager.getLogger(ItemServico.class);
	
	public UUID cadastrarItem(ItemDto itemDto, UUID postId) {
		try {
			log.log(Level.ERROR, "(Cadastrar Item)ID do post: " + postId);
			Post post = postRepo.getReferenceById(postId);
			Item item = new Item(itemDto, post);
			itemRepo.save(item);
			return item.getId();
		} catch (Exception e) {
			return null;
		}
	}
	
}
