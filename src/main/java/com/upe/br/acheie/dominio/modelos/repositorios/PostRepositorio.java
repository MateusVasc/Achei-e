package com.upe.br.acheie.dominio.modelos.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.br.acheie.dominio.modelos.Post;

public interface PostRepositorio extends JpaRepository<Post, UUID> {

}
