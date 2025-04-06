package com.upe.br.acheie.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.br.acheie.domain.models.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

  @Query("SELECT c FROM Comment c WHERE c.user.id = :userId AND c.post.id = :postId AND c.id = :commentId")
  Optional<Comment> findByUserIdAndPostIdAndCommentId(
      @Param("userId") UUID userId,
      @Param("postId") UUID postId,
      @Param("commentId") UUID commentId
  );
}
