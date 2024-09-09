package com.upe.br.acheie.controller;

import com.upe.br.acheie.domain.dto.request.TextSearchRequest;
import com.upe.br.acheie.domain.dto.request.RegisterCommentRequest;
import com.upe.br.acheie.domain.dto.request.RegisterPostRequest;
import com.upe.br.acheie.domain.dto.request.CloseSearchRequest;
import com.upe.br.acheie.domain.dto.response.RegisterCommentResponse;
import com.upe.br.acheie.domain.dto.response.RegisterPostResponse;
import com.upe.br.acheie.domain.dto.response.CloseSearchResponse;
import com.upe.br.acheie.domain.dto.response.RemoveCommentResponse;
import com.upe.br.acheie.domain.dto.response.RemovePostResponse;
import com.upe.br.acheie.domain.enums.Category;
import com.upe.br.acheie.domain.enums.Status;
import com.upe.br.acheie.domain.enums.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upe.br.acheie.domain.dto.ErrorDto;
import com.upe.br.acheie.domain.dto.PostDto;
import com.upe.br.acheie.domain.enums.Atualizacao;
import com.upe.br.acheie.service.CommentService;
import com.upe.br.acheie.service.PostService;

@RestController
@RequestMapping("/achei-e")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  private final CommentService commentService;

  private static final Logger log = LogManager.getLogger(PostController.class);

  @PostMapping("/novo-post/{usuarioId}")
  public ResponseEntity<RegisterPostResponse> cadastrarPost(@PathVariable UUID usuarioId,
      @RequestBody RegisterPostRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.postService.cadastrarPost(usuarioId, request));
  }

  @GetMapping("/post/{id}")
  public ResponseEntity<PostDto> buscarPostPorId(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.buscarPostEspecifico(id));
  }

  @GetMapping("/posts")
  public ResponseEntity<List<PostDto>> buscarPosts() {
    return ResponseEntity.status(HttpStatus.OK).body(postService.buscarPosts());
  }

  @GetMapping("/posts/{idUsuario}")
  public ResponseEntity<List<PostDto>> buscarPostsPorIdUsuario(@PathVariable UUID idUsuario) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.buscarPostsPorIdUsuario(idUsuario));
  }

  @GetMapping("/posts/texto")
  public ResponseEntity<List<PostDto>> buscarPostsPorTexto(
      @RequestBody TextSearchRequest request) {
    return ResponseEntity.ok(
        this.postService.buscarPostsPorTexto(request.texto(), request.campos(), request.limite()));
  }

  @PostMapping("/post/{postId}")
  public ResponseEntity<RegisterCommentResponse> cadastrarComentario(@PathVariable UUID postId,
      @RequestParam("usuarioId") UUID usuarioId, @RequestBody RegisterCommentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.commentService.cadastrarComentario(postId, usuarioId, request));
  }

  @PutMapping("/post")
  public ResponseEntity<Atualizacao> atualizarPost(@RequestParam(value = "postId") UUID postId,
      @RequestBody RegisterPostRequest request) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(this.postService.atualizarPost(postId, request));
  }

  @GetMapping("/posts-tipo") // tem que validar os dados que são passados para o filtro
  public ResponseEntity<List<PostDto>> filtrarPostsPorTipo(
      @RequestParam(value = "tipo", required = true) Type type) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.filtrarPostsPorTipo(type));
  }


  @GetMapping("/posts-categoria")
  public ResponseEntity<List<PostDto>> filtrarPostsPorCategoria(
      @RequestParam(value = "categoria", required = true) Category category) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(postService.filtrarPostsPorCategoria(category));
  }

  @GetMapping("/posts-estado")
  public ResponseEntity<List<PostDto>> filtrarPostsPorEstado(
      @RequestParam(value = "estado", required = true) Status status) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.filtrarPostsPorEstado(status));
  }

  @GetMapping("/posts-data")
  public ResponseEntity<List<PostDto>> filtrarPostsPorData(
      @RequestParam(value = "inicio", required = true) LocalDate inicio,
      @RequestParam(value = "fim") LocalDate fim) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.filtrarPostsPorData(inicio, fim));
  }

  @DeleteMapping("/excluir-post/{idPost}")
  public ResponseEntity<RemovePostResponse> excluirPostPorId(@PathVariable("idPost") UUID idPost,
      @RequestParam("idUsuario") UUID idUsuario) {
    return ResponseEntity.ok(this.postService.excluirPostPorId(idPost, idUsuario));
  }

  @DeleteMapping("/post/excluir-comentario/{idPost}")
  public ResponseEntity<RemoveCommentResponse> excluirComentarioPorId(
      @PathVariable("idPost") UUID idPost,
      @RequestParam("idUsuario") UUID idUsuario, @RequestParam("idComentario") UUID idComentario) {
    return ResponseEntity.ok(
        this.commentService.excluirComentarioPorId(idPost, idUsuario, idComentario));
  }

  @PutMapping("/post/encerrar-procura")
  public ResponseEntity<CloseSearchResponse> encerrarProcuraDeItem(
      @RequestBody CloseSearchRequest request) {
    return ResponseEntity.ok(this.postService.encerrarProcuraDeItem(request));
  }

  public ErrorDto tratarErro(Exception e) {
    log.error(e.getMessage(), e);
    return new ErrorDto(e);
  }
}
