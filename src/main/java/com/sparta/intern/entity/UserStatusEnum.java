package com.sparta.intern.entity;

public enum UserStatusEnum {
  ACTIVE_USER(Status.USER), //일반 유저
  WITHDRAW_USER(Status.WITHDRAW_USER);

  private final String status;

  UserStatusEnum(String status) {
    this.status = status;
  }

  public String getStatus() {
    return this.status;
  }

  public static class Status {
    public static final String USER = "USER";
    public static final String WITHDRAW_USER = "NON_USER";
  }
}
