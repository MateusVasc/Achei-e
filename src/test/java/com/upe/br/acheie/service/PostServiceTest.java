package com.upe.br.acheie.service;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.upe.br.acheie.domain.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.upe.br.acheie.dtos.CommentDTO;
import com.upe.br.acheie.dtos.ItemDTO;
import com.upe.br.acheie.dtos.PostDTO;
import com.upe.br.acheie.dtos.UserDTO;
import com.upe.br.acheie.domain.models.Comment;
import com.upe.br.acheie.domain.models.Item;
import com.upe.br.acheie.domain.models.Post;
import com.upe.br.acheie.repository.ItemRepository;
import com.upe.br.acheie.repository.PostRepository;
import com.upe.br.acheie.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class PostServicoTest {
	
	@Mock
	PostRepository postRepo;
	
	@Mock
	UserRepository usuarioRepo;
	
	@Mock
	ItemRepository itemRepo;
	
	@Mock
	ItemService itemService;
	
	@Spy
	@InjectMocks
	PostServico postServico;
	
	UserDTO userDTO;
	ItemDTO itemDto;
	PostDTO postDto;
	CommentDTO commentDTO;
	Comment comment;
	Post post;
	User user;
	UUID id;
	Item item;
	
//	@BeforeEach
//	void init() {
//		itemDto = new ItemDto(Estado.PERDIDO, Categoria.ELETRONICO, "Fone de ouvido rosa perdido no LAB1", "Fone de ouvido perdido", LocalDate.now(), null);
//		usuarioDto = new UsuarioDto("Marcos", "Silva", Curso.ENGENHARIA_DE_SOFTWARE, Periodo.OITAVO, "8199540337", null);
//		user = new Usuario(usuarioDto);
//		post = new Post();
//		postDto = new PostDto(Tipo.PERDIDO, LocalDate.now(), null, this.usuarioDto, this.itemDto, null);
//		item = new Item(this.itemDto, this.post);
//		//comentarioDto = new ComentarioDto("Essa chave é minha!", usuarioDto, LocalDate.now(), null);
//		//comentario = new Comentario(comentarioDto, post, user);
//		id = UUID.randomUUID();
//		this.post.setComentarios(new ArrayList<Comentario>());
//		this.post.setUsuario(user);
//		this.post.setItem(item);
//	}
	
//	@Test
//	@DisplayName("Deve criar um post com sucesso")
//	void cadastrarPostCase1() {
//		when(this.usuarioRepo.getReferenceById(this.user.getId())).thenReturn(this.user);
//		when(this.postRepo.save(Mockito.any(Post.class))).thenReturn(this.post);
//		when(this.itemServico.cadastrarItem(this.itemDto, this.post.getId())).thenReturn(this.id);
//
//		//when(this.postRepo.getReferenceById(this.post.getId())).thenReturn(this.post);
//		//when(this.itemRepo.save(Mockito.any(Item.class))).thenReturn(this.item);
//
//		when(this.itemRepo.getReferenceById(this.id)).thenReturn(this.item);
//
//		Cadastro estadoCadastro = this.postServico.cadastrarPost(this.user.getId(), this.postDto);
//		Assertions.assertEquals(Cadastro.SUCESSO_CADASTRO, estadoCadastro);
//	}
	
//	@Test
//	@DisplayName("Deve falhar na criação de post porque ele é nulo")
//	void cadastrarPostCase2() {
//		when(this.usuarioRepo.getReferenceById(this.user.getId())).thenReturn(this.user);
//		when(this.postRepo.save(Mockito.any(Post.class))).thenThrow(IllegalArgumentException.class);
//		doNothing().when(this.postServico).tratarErros(ArgumentMatchers.<IllegalArgumentException>any());
//		//when(this.itemServico.cadastrarItem(this.itemDto, this.post.getId())).thenReturn(this.id);
//		//when(this.postRepo.getReferenceById(this.post.getId())).thenReturn(this.post);
//		//when(this.itemRepo.save(Mockito.any(Item.class))).thenReturn(this.item);
//
//		//when(this.itemRepo.getReferenceById(this.id)).thenReturn(this.item);
//
//		Cadastro estadoCadastro = this.postServico.cadastrarPost(this.user.getId(), this.postDto);
//		Assertions.assertEquals(Cadastro.ERRO_CADASTRO, estadoCadastro);
//	}
	
	@Test
	@DisplayName("Deve obter um post específico com sucesso")
	void buscarPostEspecificoCase1() {
		when(this.postRepo.findById(this.post.getId())).thenReturn(Optional.of(this.post));
		
		PostDTO localPostDTO = this.postServico.buscarPostEspecifico(this.post.getId());
		
		Assertions.assertNotNull(localPostDTO);
	}
	
	@Test
	@DisplayName("Não deve obter nenhum post. Optional#Empty")
	void buscarPostEspecificoCase2() {
		when(this.postRepo.findById(this.post.getId())).thenReturn(Optional.empty());
		
		PostDTO localPostDTO = this.postServico.buscarPostEspecifico(this.post.getId());
		
		Assertions.assertNull(localPostDTO);
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
		
		List<PostDTO> postsDto = this.postServico.buscarPosts();
		
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
