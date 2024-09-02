package com.sparta.intern.dto;

import com.sparta.intern.entity.UserStatusEnum;
import com.sparta.intern.exception.ErrorType;
import lombok.Getter;

@Getter
public class ForbiddenResponseDto {
  private UserStatusEnum statusEnum;
  private int status;
  private String message;

  public ForbiddenResponseDto(UserStatusEnum statusEnum, ErrorType errorType) {
    this.statusEnum = statusEnum;
    this.status = errorType.getHttpStatus().value();
    this.message = errorType.getMessage();
  }



}
