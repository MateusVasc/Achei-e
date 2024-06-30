package com.upe.br.acheie.controlador;

import com.upe.br.acheie.dominio.dto.request.BuscaPorTextoRequest;
import com.upe.br.acheie.dominio.dto.request.CadastrarComentarioRequest;
import com.upe.br.acheie.dominio.dto.request.CadastrarPostRequest;
import com.upe.br.acheie.dominio.dto.request.EncerrarProcuraRequest;
import com.upe.br.acheie.dominio.dto.response.CadastrarComentarioResponse;
import com.upe.br.acheie.dominio.dto.response.CadastroPostResponse;
import com.upe.br.acheie.dominio.dto.response.EncerrarProcuraResponse;
import com.upe.br.acheie.dominio.dto.response.ExcluirComentarioResponse;
import com.upe.br.acheie.dominio.dto.response.ExcluirPostResponse;
import com.upe.br.acheie.dominio.utils.enums.Cadastro;
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

import com.upe.br.acheie.dominio.dto.ComentarioDto;
import com.upe.br.acheie.dominio.dto.ErroDto;
import com.upe.br.acheie.dominio.dto.PostDto;
import com.upe.br.acheie.dominio.utils.enums.Atualizacao;
import com.upe.br.acheie.dominio.utils.enums.Categoria;
import com.upe.br.acheie.dominio.utils.enums.Estado;
import com.upe.br.acheie.dominio.utils.enums.Tipo;
import com.upe.br.acheie.servico.ComentarioServico;
import com.upe.br.acheie.servico.PostServico;

@RestController
@RequestMapping("/achei-e")
@RequiredArgsConstructor
public class PostControle {

  private final PostServico postServico;

  private final ComentarioServico comentarioServico;

  private static final Logger log = LogManager.getLogger(PostControle.class);

  @PostMapping("/novo-post/{usuarioId}")
  public ResponseEntity<CadastroPostResponse> cadastrarPost(@PathVariable UUID usuarioId,
      @RequestBody CadastrarPostRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.postServico.cadastrarPost(usuarioId, request));
  }

  @GetMapping("/post/{id}")
  public ResponseEntity<PostDto> buscarPostPorId(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(postServico.buscarPostEspecifico(id));
  }

  @GetMapping("/posts")
  public ResponseEntity<List<PostDto>> buscarPosts() {
    return ResponseEntity.status(HttpStatus.OK).body(postServico.buscarPosts());
  }

  @GetMapping("/posts/{idUsuario}")
  public ResponseEntity<List<PostDto>> buscarPostsPorIdUsuario(@PathVariable UUID idUsuario) {
    return ResponseEntity.status(HttpStatus.OK).body(postServico.buscarPostsPorIdUsuario(idUsuario));
  }

  @GetMapping("/posts/texto")
  public ResponseEntity<List<PostDto>> buscarPostsPorTexto(
      @RequestBody BuscaPorTextoRequest request) {
    return ResponseEntity.ok(
        this.postServico.buscarPostsPorTexto(request.texto(), request.campos(), request.limite()));
  }

  @PostMapping("/post/{postId}")
  public ResponseEntity<CadastrarComentarioResponse> cadastrarComentario(@PathVariable UUID postId,
      @RequestParam("usuarioId") UUID usuarioId, @RequestBody CadastrarComentarioRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.comentarioServico.cadastrarComentario(postId, usuarioId, request));
  }

  @PutMapping("/post")
  public ResponseEntity<Atualizacao> atualizarPost(@RequestParam(value = "postId") UUID postId,
      @RequestBody CadastrarPostRequest request) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(this.postServico.atualizarPost(postId, request));
  }

  @GetMapping("/posts-tipo") // tem que validar os dados que são passados para o filtro
  public ResponseEntity<List<PostDto>> filtrarPostsPorTipo(
      @RequestParam(value = "tipo", required = true) Tipo tipo) {
    return ResponseEntity.status(HttpStatus.OK).body(postServico.filtrarPostsPorTipo(tipo));
  }


  @GetMapping("/posts-categoria")
  public ResponseEntity<List<PostDto>> filtrarPostsPorCategoria(
      @RequestParam(value = "categoria", required = true) Categoria categoria) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(postServico.filtrarPostsPorCategoria(categoria));
  }

  @GetMapping("/posts-estado")
  public ResponseEntity<List<PostDto>> filtrarPostsPorEstado(
      @RequestParam(value = "estado", required = true) Estado estado) {
    return ResponseEntity.status(HttpStatus.OK).body(postServico.filtrarPostsPorEstado(estado));
  }

  @GetMapping("/posts-data")
  public ResponseEntity<List<PostDto>> filtrarPostsPorData(
      @RequestParam(value = "inicio", required = true) LocalDate inicio,
      @RequestParam(value = "fim") LocalDate fim) {
    return ResponseEntity.status(HttpStatus.OK).body(postServico.filtrarPostsPorData(inicio, fim));
  }

  @DeleteMapping("/excluir-post/{idPost}")
  public ResponseEntity<ExcluirPostResponse> excluirPostPorId(@PathVariable("idPost") UUID idPost,
      @RequestParam("idUsuario") UUID idUsuario) {
    return ResponseEntity.ok(this.postServico.excluirPostPorId(idPost, idUsuario));
  }

  @DeleteMapping("/post/excluir-comentario/{idPost}")
  public ResponseEntity<ExcluirComentarioResponse> excluirComentarioPorId(
      @PathVariable("idPost") UUID idPost,
      @RequestParam("idUsuario") UUID idUsuario, @RequestParam("idComentario") UUID idComentario) {
    return ResponseEntity.ok(
        this.comentarioServico.excluirComentarioPorId(idPost, idUsuario, idComentario));
  }

  @PutMapping("/post/encerrar-procura")
  public ResponseEntity<EncerrarProcuraResponse> encerrarProcuraDeItem(
      @RequestBody EncerrarProcuraRequest request) {
    return ResponseEntity.ok(this.postServico.encerrarProcuraDeItem(request));
  }

  public ErroDto tratarErro(Exception e) {
    log.error(e.getMessage(), e);
    return new ErroDto(e);
  }
}
