package com.upe.br.acheie.servico;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.upe.br.acheie.dominio.dto.ItemDto;
import com.upe.br.acheie.dominio.modelos.Item;
import com.upe.br.acheie.dominio.modelos.Post;
import com.upe.br.acheie.dominio.utils.enums.Categoria;
import com.upe.br.acheie.dominio.utils.enums.Estado;
import com.upe.br.acheie.repositorio.ItemRepositorio;
import com.upe.br.acheie.repositorio.PostRepositorio;

@ExtendWith(MockitoExtension.class)
public class ItemServicoTest {

	@Mock
	ItemRepositorio itemRepo;
	
	@Mock
	PostRepositorio postRepo;
	
	@Spy
	@InjectMocks
	ItemServico itemServico;
	
	Post post;
	Item item;
	ItemDto itemDto;
	
	@BeforeEach
	void init() {
		itemDto = new ItemDto(Estado.PERDIDO, Categoria.ELETRONICO, "Fone de ouvido rosa perdido no LAB1", "Fone de ouvido perdido", LocalDate.now(), null);
		post = new Post();
		item = new Item(itemDto, this.post);
		this.post.setItem(item);
	}
	
	@Test
	@DisplayName("Deve cadastrar um item com sucesso")
	void cadastrarItemCase1() {
		when(this.postRepo.getReferenceById(this.post.getId())).thenReturn(this.post);
		 when(this.itemRepo.save(Mockito.any(Item.class))).thenAnswer(invocation -> {
		        Item savedItem = invocation.getArgument(0);
		        savedItem.setId(UUID.randomUUID()); // Ensure the saved item has an ID
		        return savedItem;
		    });
		
		UUID idItem = this.itemServico.cadastrarItem(this.itemDto, this.post.getId());
		
		Assertions.assertNotNull(idItem);
	}
	
//	@Test
//	@DisplayName("Não deve cadastrar um item porque o ID de referência é nulo")
//	void cadastrarItemCase2() {
//		when(this.postRepo.getReferenceById(null)).thenThrow(IllegalArgumentException.class);
//		doNothing().when(this.itemServico).tratarErros(ArgumentMatchers.<IllegalArgumentException>any());
//
//		UUID idItem = this.itemServico.cadastrarItem(this.itemDto, this.post.getId());
//
//		Assertions.assertNull(idItem);
//	}
	
}
