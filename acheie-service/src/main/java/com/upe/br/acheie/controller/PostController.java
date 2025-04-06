package com.upe.br.acheie.controller;

import com.upe.br.acheie.domain.enums.Category;
import com.upe.br.acheie.domain.enums.State;
import com.upe.br.acheie.domain.enums.Type;
import com.upe.br.acheie.dtos.request.SearchByTextRequest;
import com.upe.br.acheie.dtos.request.CreateCommentRequest;
import com.upe.br.acheie.dtos.request.CreatePostRequest;
import com.upe.br.acheie.dtos.request.EndSearchRequest;
import com.upe.br.acheie.dtos.response.CreateCommentResponse;
import com.upe.br.acheie.dtos.response.CreatePostResponse;
import com.upe.br.acheie.dtos.response.EndSearchResponse;
import com.upe.br.acheie.dtos.response.DeleteCommentResponse;
import com.upe.br.acheie.dtos.response.DeletePostResponse;
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

import com.upe.br.acheie.dtos.ErrorDTO;
import com.upe.br.acheie.dtos.PostDTO;
import com.upe.br.acheie.domain.enums.Update;
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
  public ResponseEntity<CreatePostResponse> createPost(@PathVariable UUID usuarioId,
                                                       @RequestBody CreatePostRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.postService.createPost(usuarioId, request));
  }

  @GetMapping("/post/{id}")
  public ResponseEntity<PostDTO> searchPostById(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.searchPostById(id));
  }

  @GetMapping("/posts")
  public ResponseEntity<List<PostDTO>> getAllPosts() {
    return ResponseEntity.status(HttpStatus.OK).body(postService.searchPosts());
  }

  @GetMapping("/posts/{userId}")
  public ResponseEntity<List<PostDTO>> searchPostsByUserId(@PathVariable UUID idUsuario) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.searchPostsByUserId(idUsuario));
  }

  @GetMapping("/posts/text")
  public ResponseEntity<List<PostDTO>> searchPostsByText(
      @RequestBody SearchByTextRequest request) {
    return ResponseEntity.ok(
        this.postService.searchPostsByText(request.text(), request.fields(), request.limit()));
  }

  @PostMapping("/post/{postId}")
  public ResponseEntity<CreateCommentResponse> createComment(@PathVariable UUID postId,
                                                             @RequestParam("usuarioId") UUID usuarioId, @RequestBody CreateCommentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.commentService.createComment(postId, usuarioId, request));
  }

  @PutMapping("/post")
  public ResponseEntity<Update> updatePost(@RequestParam(value = "postId") UUID postId,
                                           @RequestBody CreatePostRequest request) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(this.postService.updatePost(postId, request));
  }

  @GetMapping("/posts-tipo") // tem que validar os dados que são passados para o filtro
  public ResponseEntity<List<PostDTO>> getPostsByType(
      @RequestParam(value = "tipo", required = true) Type type) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByType(type));
  }


  @GetMapping("/posts-categoria")
  public ResponseEntity<List<PostDTO>> getPostsByCategory(
      @RequestParam(value = "categoria", required = true) Category category) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(postService.getPostsByCategory(category));
  }

  @GetMapping("/posts-estado")
  public ResponseEntity<List<PostDTO>> getPostsByState(
      @RequestParam(value = "estado", required = true) State state) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByState(state));
  }

  @GetMapping("/posts-date")
  public ResponseEntity<List<PostDTO>> getPostsByDate(
      @RequestParam(value = "inicio", required = true) LocalDate inicio,
      @RequestParam(value = "fim") LocalDate fim) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByDate(inicio, fim));
  }

  @DeleteMapping("/excluir-post/{postId}")
  public ResponseEntity<DeletePostResponse> deletePostById(@PathVariable("postId") UUID idPost,
                                                           @RequestParam("userId") UUID idUsuario) {
    return ResponseEntity.ok(this.postService.deletePostById(idPost, idUsuario));
  }

  @DeleteMapping("/post/excluir-comentario/{postId}")
  public ResponseEntity<DeleteCommentResponse> deleteCommentById(
      @PathVariable("postId") UUID idPost,
      @RequestParam("userId") UUID idUsuario, @RequestParam("commentId") UUID idComentario) {
    return ResponseEntity.ok(
        this.commentService.deleteCommentById(idPost, idUsuario, idComentario));
  }

  @PutMapping("/post/encerrar-procura")
  public ResponseEntity<EndSearchResponse> endItemSearch(
      @RequestBody EndSearchRequest request) {
    return ResponseEntity.ok(this.postService.endItemSearch(request));
  }

  public ErrorDTO handleError(Exception e) {
    log.error(e.getMessage(), e);
    return new ErrorDTO(e);
  }
}
