package com.sparta.intern.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType implements ErrorCode {

  //user
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
  DUPLICATE_ID(HttpStatus.OK, "중복된 아이디입니다."),
  DUPLICATE_NICKNAME(HttpStatus.OK, "중복된 닉네임입니다."),
  WITHDRAW_USER(HttpStatus.NOT_FOUND, "탈퇴한 회원입니다."),
  REFRESH_TOKEN_MISMATCH(HttpStatus.NOT_FOUND, "REFRESH_TOKEN 값이 일치 하지 않습니다."),
  ;

  private final HttpStatus httpStatus;
  private final String message;
}
