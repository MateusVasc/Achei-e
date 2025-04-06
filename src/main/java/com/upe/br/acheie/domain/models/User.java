package com.upe.br.acheie.domain.models;


import com.upe.br.acheie.dtos.UsuarioDto;
import com.upe.br.acheie.dtos.request.CadastroRequest;
import com.upe.br.acheie.domain.enums.Major;
import com.upe.br.acheie.domain.enums.Semester;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
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
  private Major major;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Semester semester;

  @Column(nullable = false)
  private String phone;

  private byte[] image;

  @Column(name = "criacao_da_conta", nullable = false)
  private LocalDate createdAt;

  @Column(name = "remocao_da_conta")
  private LocalDate deletedAt;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Post> posts;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  @ManyToMany
  @JoinTable(
      name = "usuario_conversa",
      joinColumns = @JoinColumn(name = "id_usuario"),
      inverseJoinColumns = @JoinColumn(name = "id_conversa")
  )
  private Set<Chat> chats;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Message> messages;

  public User(CadastroRequest request) {
    this.name = request.nome();
    this.lastname = request.sobrenome();
    this.email = request.email();
    this.password = request.senha();
    this.major = request.major();
    this.semester = request.semester();
    this.phone = request.telefone();
    this.image = request.foto();
  }
  
  public User(UsuarioDto usuarioDto) {
    this.id = usuarioDto.idUsuario();
	  this.name = usuarioDto.nome();
	  this.lastname = usuarioDto.sobrenome();
	  this.major = usuarioDto.major();
	  this.semester = usuarioDto.semester();
	  this.phone = usuarioDto.telefone();
	  this.image = usuarioDto.foto();
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
