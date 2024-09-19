package com.upe.br.acheie.service;

import com.upe.br.acheie.domain.model.User;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.upe.br.acheie.domain.dto.CommentDto;
import com.upe.br.acheie.domain.dto.UserDto;
import com.upe.br.acheie.domain.model.Comentario;
import com.upe.br.acheie.domain.model.Post;
import com.upe.br.acheie.repository.ComentarioRepositorio;
import com.upe.br.acheie.repository.PostRepositorio;
import com.upe.br.acheie.repository.UsuarioRepositorio;

@ExtendWith(MockitoExtension.class)
public class ComentarioServiceTest {


  @Mock
  ComentarioRepositorio comentarioRepo;

  @Mock
  PostRepositorio postRepo;

  @Mock
  UsuarioRepositorio usuarioRepo;

  @Spy
  @InjectMocks
  CommentService commentService;

  Post post;
  User user;
  Comentario comentario;
  CommentDto commentDto;
  UserDto userDto;

  @BeforeEach
  void init() {
    user = new User();
    userDto = new UserDto(user);
    post = new Post();
    commentDto = new CommentDto("Essa chave é minha!", userDto, LocalDate.now(), null);
    comentario = new Comentario(commentDto, post, user);
  }


//  @Test
//  @DisplayName("Should create a new comment succesfully")
//  void cadastrarComentarioCase1() {
//
//    when(this.postRepo.getReferenceById(this.post.getId())).thenReturn(this.post);
//    when(this.usuarioRepo.getReferenceById(this.usuario.getId())).thenReturn(this.usuario);
//    when(this.comentarioRepo.save(Mockito.any(Comentario.class))).thenReturn(this.comentario);
//
//    Cadastro estadoCadastro = this.comentarioServico.cadastrarComentario(this.post.getId(),
//        this.usuario.getId(), this.comentarioDto);
//
//    Assertions.assertEquals(Cadastro.SUCESSO_CADASTRO, estadoCadastro);
//
//  }

//	@Test
//	@DisplayName("Should fail at creating new test due to null value at saving new object")
//	void cadastrarComentarioCase2() {
//
//		when(this.postRepo.getReferenceById(this.post.getId())).thenReturn(this.post);
//		when(this.usuarioRepo.getReferenceById(this.usuario.getId())).thenReturn(this.usuario);
//		when(this.comentarioRepo.save(Mockito.any())).thenThrow(IllegalArgumentException.class);
//		doNothing().when(this.comentarioServico).tratarErros(ArgumentMatchers.<IllegalArgumentException>any());
//
//		Cadastro estadoCadastro = this.comentarioServico.cadastrarComentario(this.post.getId(), this.usuario.getId(), this.comentarioDto);
//
//		Assertions.assertEquals(Cadastro.ERRO_CADASTRO, estadoCadastro);
//
//	}
}
