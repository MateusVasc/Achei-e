package com.upe.br.acheie.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upe.br.acheie.domain.model.Post;
import com.upe.br.acheie.domain.enums.Categoria;
import com.upe.br.acheie.domain.enums.Estado;
import com.upe.br.acheie.domain.enums.Tipo;

@Repository
public interface PostRepositorio extends PesquisaDeTextoRepositorio<Post, UUID> {


	List<Post> findByTipo(Tipo tipo);
	
	@Query("SELECT p FROM Post p INNER JOIN Item i ON p.id = i.post.id WHERE i.categoria = :categoria")
	List<Post> findByItemCategoria(Categoria categoria);
	
	@Query("SELECT p FROM Post p INNER JOIN Item i ON p.id = i.post.id WHERE i.estado = :estado")
	List<Post> findByItemEstado(Estado estado);
	
	@Query("SELECT p FROM Post p INNER JOIN Item i ON p.id = i.post.id WHERE i.data BETWEEN :inicio AND :fim")
	List<Post> findByItemData(@Param("inicio")LocalDate inicio,@Param("fim")LocalDate fim);

	@Query("SELECT p FROM Post p WHERE p.usuario.id = :usuarioId AND p.id = :postId")
	Optional<Post> findByUsuarioIdAndPostId(@Param("usuarioId") UUID usuarioId, @Param("postId") UUID postId);

	@Query("SELECT p FROM Post p WHERE p.usuario.id = :usuarioId")
	List<Post> findByUsuarioId(@Param("usuarioId") UUID usuarioId);
	
}
