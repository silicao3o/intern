package com.sparta.intern.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MismatchException extends RuntimeException {
  private final ErrorCode errorCode;
}

