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
import com.upe.br.acheie.dominio.dto.PostDto;
import com.upe.br.acheie.dominio.dto.UsuarioDto;
import com.upe.br.acheie.dominio.modelos.Item;
import com.upe.br.acheie.dominio.modelos.Post;
import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.dominio.utils.enums.Cadastro;
import com.upe.br.acheie.dominio.utils.enums.Categoria;
import com.upe.br.acheie.dominio.utils.enums.Curso;
import com.upe.br.acheie.dominio.utils.enums.Estado;
import com.upe.br.acheie.dominio.utils.enums.Periodo;
import com.upe.br.acheie.dominio.utils.enums.Tipo;
import com.upe.br.acheie.repositorio.ItemRepositorio;
import com.upe.br.acheie.repositorio.PostRepositorio;
import com.upe.br.acheie.repositorio.UsuarioRepositorio;

@ExtendWith(MockitoExtension.class)
public class PostServicoTest {
	
	@Mock
	PostRepositorio postRepo;
	
	@Mock
	UsuarioRepositorio usuarioRepo;
	
	@Mock
	ItemRepositorio itemRepo;
	
	@Mock
	ItemServico itemServico;
	
	@Spy
	@InjectMocks
	PostServico postServico;
	
	UsuarioDto usuarioDto;
	ItemDto itemDto;
	PostDto postDto;
	Post post;
	Usuario usuario;
	UUID id;
	Item item;
	
	@BeforeEach
	void init() {
		item = new Item();
		itemDto = new ItemDto(Estado.PERDIDO, Categoria.ELETRONICO, "Fone de ouvido rosa perdido no LAB1", "Fone de ouvido perdido", LocalDate.now(), null);
		usuario = new Usuario();
		usuarioDto = new UsuarioDto("Marcos", "Silva", Curso.ENGENHARIA_DE_SOFTWARE, Periodo.OITAVO, "8199540337", null);
		post = new Post();
		postDto = new PostDto(Tipo.PERDIDO, LocalDate.now(), null, this.usuarioDto, this.itemDto, null);
		id = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");
	}
	
	@Test
	@DisplayName("Deve criar um post com sucesso")
	void cadastrarPostCase1() {
		when(this.usuarioRepo.getReferenceById(this.usuario.getId())).thenReturn(this.usuario);
		when(this.postRepo.save(Mockito.any(Post.class))).thenReturn(this.post);
		when(this.itemServico.cadastrarItem(this.itemDto, this.post.getId())).thenReturn(this.id);
		
		//when(this.postRepo.getReferenceById(this.post.getId())).thenReturn(this.post);
		//when(this.itemRepo.save(Mockito.any(Item.class))).thenReturn(this.item);
		
		when(this.itemRepo.getReferenceById(this.id)).thenReturn(this.item);
		
		Cadastro estadoCadastro = this.postServico.cadastrarPost(this.usuario.getId(), this.postDto);		
		Assertions.assertEquals(Cadastro.SUCESSO_CADASTRO, estadoCadastro);
	}
	
	@Test
	@DisplayName("Deve falhar na criação de post porque ele é nulo")
	void cadastrarPostCase2() {
		when(this.usuarioRepo.getReferenceById(this.usuario.getId())).thenReturn(this.usuario);
		when(this.postRepo.save(Mockito.any(Post.class))).thenThrow(IllegalArgumentException.class);
		doNothing().when(this.postServico).tratarErros(ArgumentMatchers.<IllegalArgumentException>any());
		//when(this.itemServico.cadastrarItem(this.itemDto, this.post.getId())).thenReturn(this.id);
		//when(this.postRepo.getReferenceById(this.post.getId())).thenReturn(this.post);
		//when(this.itemRepo.save(Mockito.any(Item.class))).thenReturn(this.item);
		
		//when(this.itemRepo.getReferenceById(this.id)).thenReturn(this.item);
		
		Cadastro estadoCadastro = this.postServico.cadastrarPost(this.usuario.getId(), this.postDto);		
		Assertions.assertEquals(Cadastro.ERRO_CADASTRO, estadoCadastro);
	}

}
