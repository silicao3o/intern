package com.sparta.intern.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // 기본 생성자 생성
public class LoginRequestDto {

  private String username;

  private String password;

  @Builder
  public LoginRequestDto(String username, String password){
    this.username = username;
    this.password = password;
  }
}
