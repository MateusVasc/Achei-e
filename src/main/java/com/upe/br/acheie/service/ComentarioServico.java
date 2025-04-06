package com.upe.br.acheie.service;

import com.upe.br.acheie.dtos.request.CadastrarComentarioRequest;
import com.upe.br.acheie.dtos.response.CadastrarComentarioResponse;
import com.upe.br.acheie.dtos.response.ExcluirComentarioResponse;
import com.upe.br.acheie.repository.UsuarioRepositorio;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.domain.models.Comment;
import com.upe.br.acheie.domain.models.Post;
import com.upe.br.acheie.domain.models.User;
import com.upe.br.acheie.repository.ComentarioRepositorio;
import com.upe.br.acheie.repository.PostRepositorio;
import com.upe.br.acheie.utils.AcheieException;
import com.upe.br.acheie.utils.MensagensErro;

@Service
@RequiredArgsConstructor
public class ComentarioServico {

  private final ComentarioRepositorio comentarioRepo;

  private final PostRepositorio postRepo;

  private final UsuarioRepositorio usuarioRepo;

  public CadastrarComentarioResponse cadastrarComentario(UUID postId, UUID usuarioId,
      CadastrarComentarioRequest request) {
    Post post = postRepo.findById(postId)
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_POST_NAO_ENCONTRADO));
    User user = usuarioRepo.findById(usuarioId)
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_USUARIO_NAO_ENCONTRADO));

    Comment comment = new Comment(request.assunto(), post, user);
    comentarioRepo.save(comment);

    return new CadastrarComentarioResponse(comment.getId(), comment.getPost().getId(),
        comment.getUser().getId(), comment.getCreatedAt());
  }

  public ExcluirComentarioResponse excluirComentarioPorId(UUID idPost, UUID idUsuario,
      UUID idComentario) {
    Comment commentParaRemover = this.comentarioRepo.findByUsuarioIdAndPostIdAndComentarioId(
        idUsuario, idPost, idComentario).orElseThrow(
        () -> new AcheieException(MensagensErro.MSG_COMENTARIO_DE_USUARIO_NAO_ENCONTRADO));

    this.comentarioRepo.deleteById(idComentario);
    commentParaRemover.setDeletedAt(LocalDate.now());

    return new ExcluirComentarioResponse(commentParaRemover.getCreatedAt(),
        commentParaRemover.getDeletedAt(),
        commentParaRemover.getUser().getId(),
        commentParaRemover.getPost().getId());
  }
}
