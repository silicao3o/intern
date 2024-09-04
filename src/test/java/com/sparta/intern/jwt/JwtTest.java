package com.sparta.intern.jwt;

import com.sparta.intern.entity.UserAuthorities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTest {

  @Autowired
  private JwtTokenHelper jwtTokenHelper;

  @Test
  @DisplayName("JWT 토큰 생성 테스트")
  void testJwt() {
    String username = "test";
    UserAuthorities authorities = UserAuthorities.ROLE_USER;
    String token = jwtTokenHelper.createToken(username, authorities);
    System.out.println(token);
  }
}
