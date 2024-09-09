package com.upe.br.acheie.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.br.acheie.domain.model.Comentario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, UUID> {

  @Query("SELECT c FROM Comentario c WHERE c.usuario.id = :usuarioId AND c.post.id = :postId AND c.id = :comentarioId")
  Optional<Comentario> findByUsuarioIdAndPostIdAndComentarioId(
      @Param("usuarioId") UUID usuarioId,
      @Param("postId") UUID postId,
      @Param("comentarioId") UUID comentarioId
  );
}
