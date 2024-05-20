package com.upe.br.acheie.dominio.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.br.acheie.dominio.modelos.Comentario;

public interface ComentarioRepositorio extends JpaRepository<Comentario, UUID> {

}
