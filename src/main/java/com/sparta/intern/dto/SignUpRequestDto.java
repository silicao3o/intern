package com.sparta.intern.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

  private String username;

  private String password;

  private String nickname;

  @Builder
  public SignUpRequestDto(String newuser, String password, String nickname) {
    this.username = newuser;
    this.password = password;
    this.nickname = nickname;
  }
}
