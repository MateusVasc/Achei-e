package com.upe.br.acheie.controller;

import com.upe.br.acheie.domain.enums.Category;
import com.upe.br.acheie.domain.enums.State;
import com.upe.br.acheie.domain.enums.Type;
import com.upe.br.acheie.dtos.request.BuscaPorTextoRequest;
import com.upe.br.acheie.dtos.request.CadastrarComentarioRequest;
import com.upe.br.acheie.dtos.request.CadastrarPostRequest;
import com.upe.br.acheie.dtos.request.EncerrarProcuraRequest;
import com.upe.br.acheie.dtos.response.CadastrarComentarioResponse;
import com.upe.br.acheie.dtos.response.CadastroPostResponse;
import com.upe.br.acheie.dtos.response.EncerrarProcuraResponse;
import com.upe.br.acheie.dtos.response.ExcluirComentarioResponse;
import com.upe.br.acheie.dtos.response.ExcluirPostResponse;
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

import com.upe.br.acheie.dtos.ErroDto;
import com.upe.br.acheie.dtos.PostDto;
import com.upe.br.acheie.domain.enums.Update;
import com.upe.br.acheie.service.ComentarioServico;
import com.upe.br.acheie.service.PostServico;

@RestController
@RequestMapping("/achei-e")
@RequiredArgsConstructor
public class PostController {

  private final PostServico postServico;
  private final ComentarioServico comentarioServico;
  private static final Logger log = LogManager.getLogger(PostController.class);

  @PostMapping("/novo-post/{usuarioId}")
  public ResponseEntity<CadastroPostResponse> createPost(@PathVariable UUID usuarioId,
      @RequestBody CadastrarPostRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.postServico.cadastrarPost(usuarioId, request));
  }

  @GetMapping("/post/{id}")
  public ResponseEntity<PostDto> searchPostById(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(postServico.buscarPostEspecifico(id));
  }

  @GetMapping("/posts")
  public ResponseEntity<List<PostDto>> getAllPosts() {
    return ResponseEntity.status(HttpStatus.OK).body(postServico.buscarPosts());
  }

  @GetMapping("/posts/{idUsuario}")
  public ResponseEntity<List<PostDto>> searchPostsByUserId(@PathVariable UUID idUsuario) {
    return ResponseEntity.status(HttpStatus.OK).body(postServico.buscarPostsPorIdUsuario(idUsuario));
  }

  @GetMapping("/posts/texto")
  public ResponseEntity<List<PostDto>> searchPostsByText(
      @RequestBody BuscaPorTextoRequest request) {
    return ResponseEntity.ok(
        this.postServico.buscarPostsPorTexto(request.texto(), request.campos(), request.limite()));
  }

  @PostMapping("/post/{postId}")
  public ResponseEntity<CadastrarComentarioResponse> createComment(@PathVariable UUID postId, 
      @RequestParam("usuarioId") UUID usuarioId, @RequestBody CadastrarComentarioRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.comentarioServico.cadastrarComentario(postId, usuarioId, request));
  }

  @PutMapping("/post")
  public ResponseEntity<Update> updatePost(@RequestParam(value = "postId") UUID postId,
                                           @RequestBody CadastrarPostRequest request) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(this.postServico.atualizarPost(postId, request));
  }

  @GetMapping("/posts-tipo") // tem que validar os dados que são passados para o filtro
  public ResponseEntity<List<PostDto>> getPostsByType(
      @RequestParam(value = "tipo", required = true) Type type) {
    return ResponseEntity.status(HttpStatus.OK).body(postServico.filtrarPostsPorTipo(type));
  }


  @GetMapping("/posts-categoria")
  public ResponseEntity<List<PostDto>> getPostsByCategory(
      @RequestParam(value = "categoria", required = true) Category category) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(postServico.filtrarPostsPorCategoria(category));
  }

  @GetMapping("/posts-estado")
  public ResponseEntity<List<PostDto>> getPostsByState(
      @RequestParam(value = "estado", required = true) State state) {
    return ResponseEntity.status(HttpStatus.OK).body(postServico.filtrarPostsPorEstado(state));
  }

  @GetMapping("/posts-data")
  public ResponseEntity<List<PostDto>> getPostsByDate(
      @RequestParam(value = "inicio", required = true) LocalDate inicio,
      @RequestParam(value = "fim") LocalDate fim) {
    return ResponseEntity.status(HttpStatus.OK).body(postServico.filtrarPostsPorData(inicio, fim));
  }

  @DeleteMapping("/excluir-post/{idPost}")
  public ResponseEntity<ExcluirPostResponse> deletePostById(@PathVariable("idPost") UUID idPost,
      @RequestParam("idUsuario") UUID idUsuario) {
    return ResponseEntity.ok(this.postServico.excluirPostPorId(idPost, idUsuario));
  }

  @DeleteMapping("/post/excluir-comentario/{idPost}")
  public ResponseEntity<ExcluirComentarioResponse> deleteCommentById(
      @PathVariable("idPost") UUID idPost,
      @RequestParam("idUsuario") UUID idUsuario, @RequestParam("idComentario") UUID idComentario) {
    return ResponseEntity.ok(
        this.comentarioServico.excluirComentarioPorId(idPost, idUsuario, idComentario));
  }

  @PutMapping("/post/encerrar-procura")
  public ResponseEntity<EncerrarProcuraResponse> endItemSearch(
      @RequestBody EncerrarProcuraRequest request) {
    return ResponseEntity.ok(this.postServico.encerrarProcuraDeItem(request));
  }

  public ErroDto handleError(Exception e) {
    log.error(e.getMessage(), e);
    return new ErroDto(e);
  }
}
