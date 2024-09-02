package com.sparta.intern.dto;

import com.sparta.intern.entity.User;
import com.sparta.intern.entity.UserAuthorities;
import lombok.Getter;

@Getter
public class SignUpResponseDto {
  private String username;
  private String nickname;
  private UserAuthorities authorities;

  public SignUpResponseDto(User user){
    this.username = user.getUsername();
    this.nickname = user.getNickname();
    this.authorities = user.getAuthorities();
  }
}
