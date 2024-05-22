package com.upe.br.acheie.dominio.repositorio;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.br.acheie.dominio.modelos.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {

}
