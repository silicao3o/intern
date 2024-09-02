package com.sparta.intern.exception;

import org.springframework.security.authentication.DisabledException;

public class WithDrawException extends DisabledException {

  public WithDrawException(String msg) {
    super(msg);
  }
}
