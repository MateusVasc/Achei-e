package com.upe.br.acheie.service;

import com.upe.br.acheie.domain.dto.request.RegisterCommentRequest;
import com.upe.br.acheie.domain.dto.response.RegisterCommentResponse;
import com.upe.br.acheie.domain.dto.response.RemoveCommentResponse;
import com.upe.br.acheie.domain.entities.User;
import com.upe.br.acheie.repository.UserRepository;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.domain.entities.Comment;
import com.upe.br.acheie.domain.entities.Post;
import com.upe.br.acheie.repository.CommentRepository;
import com.upe.br.acheie.repository.PostRepository;
import com.upe.br.acheie.domain.exceptions.AcheieException;
import com.upe.br.acheie.domain.exceptions.ErrorMessage;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository comentarioRepo;

  private final PostRepository postRepo;

  private final UserRepository usuarioRepo;

  public RegisterCommentResponse registerComment(UUID postId, UUID usuarioId,
      RegisterCommentRequest request) {
    Post post = postRepo.findById(postId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_POST_NAO_ENCONTRADO));
    User user = usuarioRepo.findById(usuarioId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));

    Comment comment = new Comment(request.subject(), post, user);
    comentarioRepo.save(comment);

    return new RegisterCommentResponse(comment.getId(), comment.getPost().getId(),
        comment.getUser().getId(), comment.getCreatedAt());
  }

  public RemoveCommentResponse removeCommentById(UUID idPost, UUID idUsuario,
      UUID idComentario) {
    Comment commentToRemove = this.comentarioRepo.findByUsuarioIdAndPostIdAndComentarioId(
        idUsuario, idPost, idComentario).orElseThrow(
        () -> new AcheieException(ErrorMessage.MSG_COMENTARIO_DE_USUARIO_NAO_ENCONTRADO));

    this.comentarioRepo.deleteById(idComentario);
    commentToRemove.setRemovedAt(LocalDate.now());

    return new RemoveCommentResponse(commentToRemove.getCreatedAt(),
        commentToRemove.getRemovedAt(),
        commentToRemove.getUser().getId(),
        commentToRemove.getPost().getId());
  }
}
