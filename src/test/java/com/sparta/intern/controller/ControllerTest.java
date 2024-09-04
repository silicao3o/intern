package com.sparta.intern.controller;

import com.google.gson.Gson;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.sparta.intern.dto.LoginRequestDto;
import com.sparta.intern.dto.SignUpRequestDto;
import com.sparta.intern.repository.UserRepository;
import com.sparta.intern.service.UserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Nested
public class ControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private EntityManager entityManager;


  @Nested
  @DisplayName("회원가입 컨트롤러")
  class SignupTest{

    @Test
    @DisplayName("성공적인 회원가입 테스트")
    void signupSuccess() throws Exception {
      // given
      SignUpRequestDto requestDto = SignUpRequestDto.builder()
          .newuser("test1")
          .password("1234")
          .nickname("test1")
          .build();

      String json = new Gson().toJson(requestDto);

      // when & then
      mvc.perform(post("/signup")
              .with(csrf())
              .contentType(MediaType.APPLICATION_JSON)
              .content(json))
          .andExpect(status().isOk())
          .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception{
      //given
      LoginRequestDto requestDto = LoginRequestDto.builder()
          .username("test1")
          .password("1234")
          .build();

      String json = new Gson().toJson(requestDto);

      // when & then
      mvc.perform(post("/sign")
              .with(csrf())
              .contentType(MediaType.APPLICATION_JSON)
              .content(json))
          .andExpect(status().isOk())
          .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패 비밀번호")
    void loginFail() throws Exception{
      //given
      LoginRequestDto requestDto = LoginRequestDto.builder()
          .username("test1")
          .password("12345")
          .build();

      String json = new Gson().toJson(requestDto);

      // when & then
      mvc.perform(post("/sign")
              .with(csrf()) // 403 에러방지
              .contentType(MediaType.APPLICATION_JSON)
              .content(json))
          .andExpect(status().isUnauthorized())  // 401 Unauthorized 상태 코드 기대
          .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패 - 아이디 오류")
    void loginFailInvalidUsername() throws Exception {
      // given
      LoginRequestDto requestDto = LoginRequestDto.builder()
          .username("invalidUsername")  // 틀린 사용자 이름
          .password("1234")  // 올바른 비밀번호
          .build();

      String json = new Gson().toJson(requestDto);

      // when & then
      mvc.perform(post("/sign")
              .with(csrf()) // 403 에러방지
              .contentType(MediaType.APPLICATION_JSON)
              .content(json))
          .andExpect(status().isUnauthorized())  // 401 Unauthorized 상태 코드 기대
          .andDo(print());  // 요청과 응답 정보 출력
    }
  }

}









