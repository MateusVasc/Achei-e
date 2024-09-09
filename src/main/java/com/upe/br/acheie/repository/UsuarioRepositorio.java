package com.upe.br.acheie.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.upe.br.acheie.domain.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {

  UserDetails findByEmail(String email);
  
  @Query("SELECT u FROM Usuario u WHERE u.email = :email")
  Optional<Usuario> getByEmail(String email);

  void deleteByEmail(String email);
}
