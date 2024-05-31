package com.upe.br.acheie.repositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upe.br.acheie.dominio.modelos.Post;
import com.upe.br.acheie.dominio.utils.enums.Categoria;
import com.upe.br.acheie.dominio.utils.enums.Estado;
import com.upe.br.acheie.dominio.utils.enums.Tipo;

@Repository
public interface PostRepositorio extends JpaRepository<Post, UUID> {


	List<Post> findByTipo(Tipo tipo);
	
	@Query("SELECT p FROM Post p INNER JOIN Item i ON p.id = i.post.id WHERE i.categoria = :categoria")
	List<Post> findByItemCategoria(Categoria categoria);
	
	@Query("SELECT p FROM Post p INNER JOIN Item i ON p.id = i.post.id WHERE i.estado = :estado")
	List<Post> findByItemEstado(Estado estado);
	
	@Query("SELECT p FROM Post p INNER JOIN Item i ON p.id = i.post.id WHERE i.data BETWEEN :inicio AND :fim")
	List<Post> findByItemData(@Param("inicio")LocalDate inicio,@Param("fim")LocalDate fim);
	
}
