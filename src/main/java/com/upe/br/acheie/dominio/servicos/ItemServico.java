package com.upe.br.acheie.dominio.servicos;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dominio.dto.ItemDto;
import com.upe.br.acheie.dominio.modelos.Item;
import com.upe.br.acheie.dominio.modelos.Post;
import com.upe.br.acheie.dominio.repositorios.ItemRepositorio;
import com.upe.br.acheie.dominio.repositorios.PostRepositorio;
import com.upe.br.acheie.dominio.utils.AcheieException;
import com.upe.br.acheie.dominio.utils.MensagensErro;

@Service
public class ItemServico {

	@Autowired
	private ItemRepositorio itemRepo;
	
	@Autowired
	private PostRepositorio postRepo;
	
	private final Logger log = LogManager.getLogger(ItemServico.class);
	
	public UUID cadastrarItem(ItemDto itemDto, UUID postId) {
		try {
			Post post = postRepo.getReferenceById(postId);
			Item item = new Item(itemDto, post);
			itemRepo.save(item);
			return item.getId();
		} catch (Exception e) {
			this.tratarErros(e);
		}
		return null;
	}
	
	public void tratarErros(Exception e) {
		if (e instanceof IllegalArgumentException) {
			log.error(MensagensErro.MSG_ELEMENTO_AUSENTE, e);
			throw new AcheieException(MensagensErro.MSG_ELEMENTO_AUSENTE, e);
		} else if (e instanceof OptimisticLockingFailureException) {
			log.error(MensagensErro.MSG_ERRO_INESPERADO, e);
			throw new AcheieException(MensagensErro.MSG_ERRO_INESPERADO, e);
		} else {
			log.error(e.getMessage(), e);
			throw new AcheieException(e.getMessage(), e);
		}
	}
	
}
