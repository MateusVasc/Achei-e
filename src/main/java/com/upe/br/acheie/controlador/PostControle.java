package com.upe.br.acheie.controlador;

import com.upe.br.acheie.dominio.dto.request.EncerrarProcuraRequest;
import java.time.LocalDate;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.upe.br.acheie.dominio.utils.MensagemUtil;
import com.upe.br.acheie.dominio.utils.enums.Atualizacao;
import com.upe.br.acheie.dominio.utils.enums.Categoria;
import com.upe.br.acheie.dominio.utils.enums.Estado;
import com.upe.br.acheie.dominio.utils.enums.Tipo;
import com.upe.br.acheie.servico.ComentarioServico;
import com.upe.br.acheie.servico.PostServico;

@RestController
@RequestMapping("/achei-e")
public class PostControle {

  @Autowired
  private PostServico postServico;

  @Autowired
  private ComentarioServico comentarioServico;

  private static final Logger log = LogManager.getLogger(PostControle.class);

  @PostMapping("/novo-post/{usuarioId}")
  public ResponseEntity<?> cadastrarPost(@PathVariable UUID usuarioId,
      @RequestBody PostDto postDto) {
    try {
      this.postServico.cadastrarPost(usuarioId, postDto);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(this.tratarErro(e));
    }
  }

  @GetMapping("/post/{id}")
  public ResponseEntity<?> buscarPostPorId(@PathVariable UUID id) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(postServico.buscarPostEspecifico(id));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.tratarErro(e));
    }
  }

  @GetMapping("/posts")
  public ResponseEntity<?> buscarPosts() {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(postServico.buscarPosts());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.tratarErro(e));
    }
  }

  @PostMapping("/post/{postId}")
  public ResponseEntity<?> cadastrarComentario(@PathVariable UUID postId,
      @RequestParam("usuarioId") UUID usuarioId, @RequestBody ComentarioDto comentario) {
    try {
      this.comentarioServico.cadastrarComentario(postId, usuarioId, comentario);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.tratarErro(e));
    }
  }

  @PutMapping("/post")
  public ResponseEntity<?> atualizarPost(@RequestParam(value = "postId") UUID postId,
      @RequestBody PostDto postDto) {
    try {
      Atualizacao estadoAtualizacao = this.postServico.atualizarPost(postId, postDto);
      return ResponseEntity.status(HttpStatus.OK)
          .body(new MensagemUtil(estadoAtualizacao.getMensagem()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(this.tratarErro(e));
    }
  }

  @GetMapping("/posts-tipo") // tem que validar os dados que são passados para o filtro
  public ResponseEntity<?> filtrarPostsPorTipo(
      @RequestParam(value = "tipo", required = true) Tipo tipo) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(postServico.filtrarPostsPorTipo(tipo));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.tratarErro(e));
    }
  }


  @GetMapping("/posts-categoria")
  public ResponseEntity<?> filtrarPostsPorCategoria(
      @RequestParam(value = "categoria", required = true) Categoria categoria) {
    try {
      return ResponseEntity.status(HttpStatus.OK)
          .body(postServico.filtrarPostsPorCategoria(categoria));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.tratarErro(e));
    }
  }

  @GetMapping("/posts-estado")
  public ResponseEntity<?> filtrarPostsPorEstado(
      @RequestParam(value = "estado", required = true) Estado estado) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(postServico.filtrarPostsPorEstado(estado));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.tratarErro(e));
    }
  }

  @GetMapping("/posts-data")
  public ResponseEntity<?> filtrarPostsPorData(
      @RequestParam(value = "inicio", required = true) LocalDate inicio,
      @RequestParam(value = "fim") LocalDate fim) {
    try {
      return ResponseEntity.status(HttpStatus.OK)
          .body(postServico.filtrarPostsPorData(inicio, fim));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.tratarErro(e));
    }
  }

  @DeleteMapping("/excluir-post/{idPost}")
  public ResponseEntity<Object> excluirPostPorId(@PathVariable("idPost") UUID idPost,
      @RequestParam("idUsuario") UUID idUsuario) {
    ResponseEntity<Object> response;

    try {
      response = ResponseEntity.ok(this.postServico.excluirPostPorId(idPost, idUsuario));
    } catch (Exception e) {
      response = ResponseEntity.badRequest().body(new MensagemUtil(e.getMessage()));
    }

    return response;
  }

  @DeleteMapping("/post/excluir-comentario/{idPost}")
  public ResponseEntity<Object> excluirComentarioPorId(@PathVariable("idPost") UUID idPost,
      @RequestParam("idUsuario") UUID idUsuario, @RequestParam("idComentario") UUID idComentario) {
    ResponseEntity<Object> response;

    try {
      response = ResponseEntity.ok(this.comentarioServico.excluirComentarioPorId(idPost, idUsuario, idComentario));
    } catch (Exception e) {
      response = ResponseEntity.badRequest().body(new MensagemUtil(e.getMessage()));
    }

    return response;
  }

  @PutMapping("/post/encerrar-procura")
  public ResponseEntity<Object> encerrarProcuraDeItem(@RequestBody EncerrarProcuraRequest request) {
    ResponseEntity<Object> response;

    try {
      response = ResponseEntity.ok(this.postServico.encerrarProcuraDeItem(request));
    } catch (Exception e) {
      response = ResponseEntity.badRequest().body(new MensagemUtil(e.getMessage()));
    }

    return response;
  }

  public ErroDto tratarErro(Exception e) {
    log.error(e.getMessage(), e);
    return new ErroDto(e);
  }
}
