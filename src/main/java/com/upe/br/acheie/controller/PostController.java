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

  @PostMapping("/new-post/{userId}")
  public ResponseEntity<RegisterPostResponse> createPost(@PathVariable UUID userId,
      @RequestBody RegisterPostRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.postService.registerPost(userId, request));
  }

  @GetMapping("/post/{id}")
  public ResponseEntity<PostDto> searchById(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.searchPostById(id));
  }

  @GetMapping("/posts")
  public ResponseEntity<List<PostDto>> searchAll() {
    return ResponseEntity.status(HttpStatus.OK).body(postService.searchAllPosts());
  }

  @GetMapping("/posts/{userId}")
  public ResponseEntity<List<PostDto>> searchByUserId(@PathVariable UUID userId) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.searchPostsByUserId(userId));
  }

  @GetMapping("/posts/text")
  public ResponseEntity<List<PostDto>> searchByText(
      @RequestBody TextSearchRequest request) {
    return ResponseEntity.ok(
        this.postService.searchPostsByText(request.text(), request.fields(), request.limit()));
  }

  @PostMapping("/post/{postId}")
  public ResponseEntity<RegisterCommentResponse> makeComment(@PathVariable UUID postId,
      @RequestParam("userId") UUID userId, @RequestBody RegisterCommentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.commentService.registerComment(postId, userId, request));
  }

  @PutMapping("/post")
  public ResponseEntity<Atualizacao> updatePost(@RequestParam(value = "postId") UUID postId,
      @RequestBody RegisterPostRequest request) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(this.postService.updatePost(postId, request));
  }

  @GetMapping("/posts-type") // tem que validar os dados que são passados para o filtro
  public ResponseEntity<List<PostDto>> searchByType(
      @RequestParam(value = "type", required = true) Type type) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.searchPostsByType(type));
  }


  @GetMapping("/posts-category")
  public ResponseEntity<List<PostDto>> searchByCategory(
      @RequestParam(value = "category", required = true) Category category) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(postService.searchPostsByCategory(category));
  }

  @GetMapping("/posts-status")
  public ResponseEntity<List<PostDto>> searchByStatus(
      @RequestParam(value = "status", required = true) Status status) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.searchPostsByStatus(status));
  }

  @GetMapping("/posts-lostDate")
  public ResponseEntity<List<PostDto>> searchByLostDate(
      @RequestParam(value = "start", required = true) LocalDate start,
      @RequestParam(value = "end") LocalDate end) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.searchPostsByLostDate(start, end));
  }

  @DeleteMapping("/remove-post/{postId}")
  public ResponseEntity<RemovePostResponse> removeById(@PathVariable("postId") UUID postId,
      @RequestParam("userId") UUID userId) {
    return ResponseEntity.ok(this.postService.removePostById(postId, userId));
  }

  @DeleteMapping("/post/remove-comment/{postId}")
  public ResponseEntity<RemoveCommentResponse> removeCommentById(
      @PathVariable("postId") UUID postId,
      @RequestParam("userId") UUID userId, @RequestParam("commentId") UUID commentId) {
    return ResponseEntity.ok(
        this.commentService.removeCommentById(postId, userId, commentId));
  }

  @PutMapping("/post/close-search")
  public ResponseEntity<CloseSearchResponse> closeSearch(
      @RequestBody CloseSearchRequest request) {
    return ResponseEntity.ok(this.postService.closeSearch(request));
  }

  public ErrorDto handleError(Exception e) {
    log.error(e.getMessage(), e);
    return new ErrorDto(e);
  }
}
