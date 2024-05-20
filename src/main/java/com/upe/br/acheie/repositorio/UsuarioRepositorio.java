package com.upe.br.acheie.repositorio;

import com.upe.br.acheie.dominio.modelos.Usuario;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {

  UserDetails findByEmail(String email);

  void deleteByEmail(String email);
}
