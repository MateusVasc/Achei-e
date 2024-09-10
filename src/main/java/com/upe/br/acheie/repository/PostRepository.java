package com.upe.br.acheie.repository;

import com.upe.br.acheie.domain.enums.Category;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upe.br.acheie.domain.model.Post;
import com.upe.br.acheie.domain.enums.Status;
import com.upe.br.acheie.domain.enums.Type;

@Repository
public interface PostRepository extends TextSearchRepository<Post, UUID> {


	List<Post> findByType(Type type);
	
	@Query("SELECT p FROM Post p INNER JOIN Item i ON p.id = i.post.id WHERE i.category = :category")
	List<Post> findByItemCategoria(Category category);
	
	@Query("SELECT p FROM Post p INNER JOIN Item i ON p.id = i.post.id WHERE i.status = :status")
	List<Post> findByItemEstado(Status status);
	
	@Query("SELECT p FROM Post p INNER JOIN Item i ON p.id = i.post.id WHERE i.lostAt BETWEEN :inicio AND :fim")
	List<Post> findByItemData(@Param("inicio")LocalDate inicio,@Param("fim")LocalDate fim);

	@Query("SELECT p FROM Post p WHERE p.user.id = :usuarioId AND p.id = :postId")
	Optional<Post> findByUsuarioIdAndPostId(@Param("usuarioId") UUID usuarioId, @Param("postId") UUID postId);

	@Query("SELECT p FROM Post p WHERE p.user.id = :usuarioId")
	List<Post> findByUsuarioId(@Param("usuarioId") UUID usuarioId);
	
}
