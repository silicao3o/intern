package com.sparta.intern.entity;

public enum UserAuthorities {
  ROLE_USER("ROLE_USER"),
  ROLE_ADMIN("ROLE_ADMIN");

  private final String authorityName;

  UserAuthorities(String authorityName) {
    this.authorityName = authorityName;
  }

  public String getAuthorityName() {
    return authorityName;
  }
}
