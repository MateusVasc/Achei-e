package com.upe.br.acheie.repository;

import java.util.Optional;
import java.util.UUID;

import com.upe.br.acheie.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  
  @Query("SELECT u FROM User u WHERE u.email = :email")
  Optional<User> getByEmail(String email);

  void deleteByEmail(String email);
}
