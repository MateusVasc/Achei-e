package com.upe.br.acheie.repositorio;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.upe.br.acheie.dominio.modelos.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {

  UserDetails findByEmail(String email);
  
  @Query("SELECT u FROM Usuario u WHERE u.email = :email")
  Usuario getByEmail(String email);

  void deleteByEmail(String email);
}
