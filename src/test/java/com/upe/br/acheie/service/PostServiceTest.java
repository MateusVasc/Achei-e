package com.upe.br.acheie.service;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.upe.br.acheie.domain.dto.CommentDto;
import com.upe.br.acheie.domain.dto.ItemDto;
import com.upe.br.acheie.domain.dto.PostDto;
import com.upe.br.acheie.domain.dto.UserDto;
import com.upe.br.acheie.domain.model.Comentario;
import com.upe.br.acheie.domain.model.Item;
import com.upe.br.acheie.domain.model.Post;
import com.upe.br.acheie.domain.model.Usuario;
import com.upe.br.acheie.repository.ItemRepositorio;
import com.upe.br.acheie.repository.PostRepositorio;
import com.upe.br.acheie.repository.UsuarioRepositorio;

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
	
	UserDto userDto;
	ItemDto itemDto;
	PostDto postDto;
	CommentDto commentDto;
	Comentario comentario;
	Post post;
	Usuario usuario;
	UUID id;
	Item item;
	
//	@BeforeEach
//	void init() {
//		itemDto = new ItemDto(Estado.PERDIDO, Categoria.ELETRONICO, "Fone de ouvido rosa perdido no LAB1", "Fone de ouvido perdido", LocalDate.now(), null);
//		usuarioDto = new UsuarioDto("Marcos", "Silva", Curso.ENGENHARIA_DE_SOFTWARE, Periodo.OITAVO, "8199540337", null);
//		usuario = new Usuario(usuarioDto);
//		post = new Post();
//		postDto = new PostDto(Tipo.PERDIDO, LocalDate.now(), null, this.usuarioDto, this.itemDto, null);
//		item = new Item(this.itemDto, this.post);
//		//comentarioDto = new ComentarioDto("Essa chave é minha!", usuarioDto, LocalDate.now(), null);
//		//comentario = new Comentario(comentarioDto, post, usuario);
//		id = UUID.randomUUID();
//		this.post.setComentarios(new ArrayList<Comentario>());
//		this.post.setUsuario(usuario);
//		this.post.setItem(item);
//	}
	
//	@Test
//	@DisplayName("Deve criar um post com sucesso")
//	void cadastrarPostCase1() {
//		when(this.usuarioRepo.getReferenceById(this.usuario.getId())).thenReturn(this.usuario);
//		when(this.postRepo.save(Mockito.any(Post.class))).thenReturn(this.post);
//		when(this.itemServico.cadastrarItem(this.itemDto, this.post.getId())).thenReturn(this.id);
//
//		//when(this.postRepo.getReferenceById(this.post.getId())).thenReturn(this.post);
//		//when(this.itemRepo.save(Mockito.any(Item.class))).thenReturn(this.item);
//
//		when(this.itemRepo.getReferenceById(this.id)).thenReturn(this.item);
//
//		Cadastro estadoCadastro = this.postServico.cadastrarPost(this.usuario.getId(), this.postDto);
//		Assertions.assertEquals(Cadastro.SUCESSO_CADASTRO, estadoCadastro);
//	}
	
//	@Test
//	@DisplayName("Deve falhar na criação de post porque ele é nulo")
//	void cadastrarPostCase2() {
//		when(this.usuarioRepo.getReferenceById(this.usuario.getId())).thenReturn(this.usuario);
//		when(this.postRepo.save(Mockito.any(Post.class))).thenThrow(IllegalArgumentException.class);
//		doNothing().when(this.postServico).tratarErros(ArgumentMatchers.<IllegalArgumentException>any());
//		//when(this.itemServico.cadastrarItem(this.itemDto, this.post.getId())).thenReturn(this.id);
//		//when(this.postRepo.getReferenceById(this.post.getId())).thenReturn(this.post);
//		//when(this.itemRepo.save(Mockito.any(Item.class))).thenReturn(this.item);
//
//		//when(this.itemRepo.getReferenceById(this.id)).thenReturn(this.item);
//
//		Cadastro estadoCadastro = this.postServico.cadastrarPost(this.usuario.getId(), this.postDto);
//		Assertions.assertEquals(Cadastro.ERRO_CADASTRO, estadoCadastro);
//	}
	
	@Test
	@DisplayName("Deve obter um post específico com sucesso")
	void buscarPostEspecificoCase1() {
		when(this.postRepo.findById(this.post.getId())).thenReturn(Optional.of(this.post));
		
		PostDto localPostDto = this.postServico.buscarPostEspecifico(this.post.getId());
		
		Assertions.assertNotNull(localPostDto);
	}
	
	@Test
	@DisplayName("Não deve obter nenhum post. Optional#Empty")
	void buscarPostEspecificoCase2() {
		when(this.postRepo.findById(this.post.getId())).thenReturn(Optional.empty());
		
		PostDto localPostDto = this.postServico.buscarPostEspecifico(this.post.getId());
		
		Assertions.assertNull(localPostDto);
	}
	
//	@Test
//	@DisplayName("Deve lançar exceção pelo ID ser nulo no momento da busca")
//	void buscarPostEspecificoCase3() {
//		when(this.postRepo.findById(this.post.getId())).thenThrow(IllegalArgumentException.class);
//		doNothing().when(this.postServico).tratarErros(ArgumentMatchers.<IllegalArgumentException>any());
//		PostDto localPostDto = this.postServico.buscarPostEspecifico(null);
//
//		Assertions.assertNull(localPostDto);
//	}
	
	@Test
	@DisplayName("Deve obter todos os posts")
	void buscarPostsCase1() {
		when(this.postRepo.findAll()).thenReturn(List.of(this.post));
		
		List<PostDto> postsDto = this.postServico.buscarPosts();
		
		Assertions.assertNotNull(postsDto);
	}
	
//	@Test
//	@DisplayName("Deve lançar exceção por findAll() retornar null")
//	void buscarPostsCase2() {
//		when(this.postRepo.findAll()).thenReturn(null);
//		doNothing().when(this.postServico).tratarErros(ArgumentMatchers.<NullPointerException>any());
//
//		List<PostDto> postsDto = this.postServico.buscarPosts();
//
//		Assertions.assertEquals(List.of(), postsDto);
//	}
	
	

}
