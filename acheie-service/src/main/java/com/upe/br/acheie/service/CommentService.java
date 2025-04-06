package com.upe.br.acheie.service;

import com.upe.br.acheie.dtos.request.CreateCommentRequest;
import com.upe.br.acheie.dtos.response.CreateCommentResponse;
import com.upe.br.acheie.dtos.response.DeleteCommentResponse;
import com.upe.br.acheie.repository.UserRepository;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.domain.models.Comment;
import com.upe.br.acheie.domain.models.Post;
import com.upe.br.acheie.domain.models.User;
import com.upe.br.acheie.repository.CommentRepository;
import com.upe.br.acheie.repository.PostRepository;
import com.upe.br.acheie.utils.AcheieException;
import com.upe.br.acheie.utils.ErrorMessages;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public CreateCommentResponse createComment(UUID postId, UUID userId,
                                             CreateCommentRequest request) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_POST_NOT_FOUND));
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_USER_NOT_FOUND));

    Comment comment = new Comment(request.subject(), post, user);
    commentRepository.save(comment);

    return new CreateCommentResponse(comment.getId(), comment.getPost().getId(),
        comment.getUser().getId(), comment.getCreatedAt());
  }

  public DeleteCommentResponse deleteCommentById(UUID postId, UUID userId,
                                                 UUID commentId) {
    Comment commentToDelete = this.commentRepository.findByUserIdAndPostIdAndCommentId(
        userId, postId, commentId).orElseThrow(
        () -> new AcheieException(ErrorMessages.MSG_USER_COMMENT_NOT_FOUND));

    this.commentRepository.deleteById(commentId);
    commentToDelete.setDeletedAt(LocalDate.now());

    return new DeleteCommentResponse(commentToDelete.getCreatedAt(),
        commentToDelete.getDeletedAt(),
        commentToDelete.getUser().getId(),
        commentToDelete.getPost().getId());
  }
}
