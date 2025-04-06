package com.upe.br.acheie.service;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.upe.br.acheie.dtos.CommentDTO;
import com.upe.br.acheie.dtos.UserDTO;
import com.upe.br.acheie.domain.models.Comment;
import com.upe.br.acheie.domain.models.Post;
import com.upe.br.acheie.domain.models.User;
import com.upe.br.acheie.repository.CommentRepository;
import com.upe.br.acheie.repository.PostRepository;
import com.upe.br.acheie.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {


  @Mock
  CommentRepository comentarioRepo;

  @Mock
  PostRepository postRepo;

  @Mock
  UserRepository usuarioRepo;

  @Spy
  @InjectMocks
  CommentService commentService;

  Post post;
  User user;
  Comment comment;
  CommentDTO commentDTO;
  UserDTO userDTO;

  @BeforeEach
  void init() {
    user = new User();
    userDTO = new UserDTO(user);
    post = new Post();
    commentDTO = new CommentDTO("Essa chave é minha!", userDTO, LocalDate.now(), null);
    comment = new Comment(commentDTO, post, user);
  }


//  @Test
//  @DisplayName("Should create a new comment succesfully")
//  void cadastrarComentarioCase1() {
//
//    when(this.postRepo.getReferenceById(this.post.getId())).thenReturn(this.post);
//    when(this.usuarioRepo.getReferenceById(this.user.getId())).thenReturn(this.user);
//    when(this.comentarioRepo.save(Mockito.any(Comentario.class))).thenReturn(this.comentario);
//
//    Cadastro estadoCadastro = this.comentarioServico.cadastrarComentario(this.post.getId(),
//        this.user.getId(), this.comentarioDto);
//
//    Assertions.assertEquals(Cadastro.SUCESSO_CADASTRO, estadoCadastro);
//
//  }

//	@Test
//	@DisplayName("Should fail at creating new test due to null value at saving new object")
//	void cadastrarComentarioCase2() {
//
//		when(this.postRepo.getReferenceById(this.post.getId())).thenReturn(this.post);
//		when(this.usuarioRepo.getReferenceById(this.user.getId())).thenReturn(this.user);
//		when(this.comentarioRepo.save(Mockito.any())).thenThrow(IllegalArgumentException.class);
//		doNothing().when(this.comentarioServico).tratarErros(ArgumentMatchers.<IllegalArgumentException>any());
//
//		Cadastro estadoCadastro = this.comentarioServico.cadastrarComentario(this.post.getId(), this.user.getId(), this.comentarioDto);
//
//		Assertions.assertEquals(Cadastro.ERRO_CADASTRO, estadoCadastro);
//
//	}
}
