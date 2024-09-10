package com.upe.br.acheie.repository;

import com.upe.br.acheie.domain.entities.User;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  UserDetails findByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.email = :email")
  Optional<User> getByEmail(String email);

  void deleteByEmail(String email);
}
