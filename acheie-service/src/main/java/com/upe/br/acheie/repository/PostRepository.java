package com.upe.br.acheie.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.upe.br.acheie.domain.enums.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upe.br.acheie.domain.models.Post;
import com.upe.br.acheie.domain.enums.Category;
import com.upe.br.acheie.domain.enums.State;

@Repository
public interface PostRepository extends TextSearchRepository<Post, UUID> {


	List<Post> findByType(Type type);
	
	@Query("SELECT p FROM Post p INNER JOIN Item i ON p.id = i.post.id WHERE i.category = :category")
	List<Post> findByItemCategory(Category category);
	
	@Query("SELECT p FROM Post p INNER JOIN Item i ON p.id = i.post.id WHERE i.state = :state")
	List<Post> findByItemState(State state);
	
	@Query("SELECT p FROM Post p INNER JOIN Item i ON p.id = i.post.id WHERE i.date BETWEEN :start AND :end")
	List<Post> findByItemDate(@Param("start")LocalDate start, @Param("end")LocalDate end);

	@Query("SELECT p FROM Post p WHERE p.user.id = :userId AND p.id = :postId")
	Optional<Post> findByUserIdAndPostId(@Param("userId") UUID userId, @Param("postId") UUID postId);

	@Query("SELECT p FROM Post p WHERE p.user.id = :userId")
	List<Post> findByUserId(@Param("userId") UUID userId);
	
}
