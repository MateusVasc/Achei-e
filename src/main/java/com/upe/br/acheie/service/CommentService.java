package com.upe.br.acheie.service;

import com.upe.br.acheie.domain.dto.request.RegisterCommentRequest;
import com.upe.br.acheie.domain.dto.response.RegisterCommentResponse;
import com.upe.br.acheie.domain.dto.response.RemoveCommentResponse;
import com.upe.br.acheie.domain.model.User;
import com.upe.br.acheie.repository.UserRepository;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.domain.model.Comment;
import com.upe.br.acheie.domain.model.Post;
import com.upe.br.acheie.repository.CommentRepository;
import com.upe.br.acheie.repository.PostRepositorio;
import com.upe.br.acheie.domain.exceptions.AcheieException;
import com.upe.br.acheie.domain.exceptions.ErrorMessage;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository comentarioRepo;

  private final PostRepositorio postRepo;

  private final UserRepository usuarioRepo;

  public RegisterCommentResponse cadastrarComentario(UUID postId, UUID usuarioId,
      RegisterCommentRequest request) {
    Post post = postRepo.findById(postId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_POST_NAO_ENCONTRADO));
    User user = usuarioRepo.findById(usuarioId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));

    Comment comment = new Comment(request.assunto(), post, user);
    comentarioRepo.save(comment);

    return new RegisterCommentResponse(comment.getId(), comment.getPost().getId(),
        comment.getUser().getId(), comment.getCreatedAt());
  }

  public RemoveCommentResponse excluirComentarioPorId(UUID idPost, UUID idUsuario,
      UUID idComentario) {
    Comment commentParaRemover = this.comentarioRepo.findByUsuarioIdAndPostIdAndComentarioId(
        idUsuario, idPost, idComentario).orElseThrow(
        () -> new AcheieException(ErrorMessage.MSG_COMENTARIO_DE_USUARIO_NAO_ENCONTRADO));

    this.comentarioRepo.deleteById(idComentario);
    commentParaRemover.setRemovedAt(LocalDate.now());

    return new RemoveCommentResponse(commentParaRemover.getCreatedAt(),
        commentParaRemover.getRemovedAt(),
        commentParaRemover.getUser().getId(),
        commentParaRemover.getPost().getId());
  }
}
