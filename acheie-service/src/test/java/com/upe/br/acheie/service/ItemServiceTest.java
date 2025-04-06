package com.upe.br.acheie.service;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.UUID;

import com.upe.br.acheie.domain.enums.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.upe.br.acheie.dtos.ItemDTO;
import com.upe.br.acheie.domain.models.Item;
import com.upe.br.acheie.domain.models.Post;
import com.upe.br.acheie.domain.enums.Category;
import com.upe.br.acheie.repository.ItemRepository;
import com.upe.br.acheie.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

	@Mock
	ItemRepository itemRepo;
	
	@Mock
	PostRepository postRepo;
	
	@Spy
	@InjectMocks
	ItemService itemService;
	
	Post post;
	Item item;
	ItemDTO itemDto;
	
	@BeforeEach
	void init() {
		itemDto = new ItemDTO(State.LOST, Category.ELECTRONIC, "Fone de ouvido rosa perdido no LAB1", "Fone de ouvido perdido", LocalDate.now(), null);
		post = new Post();
		item = new Item(itemDto, this.post);
		this.post.setItem(item);
	}
	
	@Test
	@DisplayName("Deve cadastrar um item com sucesso")
	void createItemCase1() {
		when(this.postRepo.getReferenceById(this.post.getId())).thenReturn(this.post);
		 when(this.itemRepo.save(Mockito.any(Item.class))).thenAnswer(invocation -> {
		        Item savedItem = invocation.getArgument(0);
		        savedItem.setId(UUID.randomUUID()); // Ensure the saved item has an ID
		        return savedItem;
		    });
		
		UUID idItem = this.itemService.createItem(this.itemDto, this.post.getId());
		
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
