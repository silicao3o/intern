package com.sparta.intern.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  private String username;

  private String nickname;

  private String password;

  @Enumerated(EnumType.STRING)
  private UserAuthorities authorities;

  private String refreshToken;

  @Builder
  public User(String username, String nickname, String password){
    this.username = username;
    this.nickname = nickname;
    this.password = password;
    this.authorities = UserAuthorities.ROLE_USER;
  }
}
