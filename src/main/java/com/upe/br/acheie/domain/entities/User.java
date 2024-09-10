package com.upe.br.acheie.domain.entities;


import com.upe.br.acheie.domain.dto.UserDto;
import com.upe.br.acheie.domain.dto.request.RegisterRequest;
import com.upe.br.acheie.domain.enums.Course;
import com.upe.br.acheie.domain.enums.Semester;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_usuario")
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String lastname;

  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Course course;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Semester semester;

  @Column(nullable = false)
  private String telephone;

  private byte[] photo;

  @Column(name = "criacao_da_conta", nullable = false)
  private LocalDate createdAt;

  @Column(name = "remocao_da_conta")
  private LocalDate removedAt;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Post> posts;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Message> messages;

  public User(RegisterRequest request) {
    this.name = request.name();
    this.lastname = request.lastname();
    this.email = request.email();
    this.password = request.password();
    this.course = request.course();
    this.semester = request.semester();
    this.telephone = request.phone();
    this.photo = request.photo();
  }

  public User(UserDto userDto) {
    this.id = userDto.userId();
    this.name = userDto.name();
    this.lastname = userDto.lastname();
    this.course = userDto.course();
    this.semester = userDto.semester();
    this.telephone = userDto.phone();
    this.photo = userDto.photo();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("USUARIO"));
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
