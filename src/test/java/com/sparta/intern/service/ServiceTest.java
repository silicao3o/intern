package com.sparta.intern.service;

import static com.sparta.intern.exception.ErrorType.DUPLICATE_ID;
import static com.sparta.intern.exception.ErrorType.DUPLICATE_NICKNAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparta.intern.dto.SignUpRequestDto;
import com.sparta.intern.dto.SignUpResponseDto;
import com.sparta.intern.entity.User;
import com.sparta.intern.exception.CustomException;
import com.sparta.intern.exception.DuplicatedException;
import com.sparta.intern.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserService userService;

  private User testuser1;

  @BeforeEach
  void init() {
    SignUpRequestDto requestDto = new SignUpRequestDto(
        "tester",
        "1234",
        "testNickname"
    );

    testuser1 = new User(
        requestDto.getUsername(),
        requestDto.getNickname(),
        requestDto.getPassword()
    );

    userRepository.save(testuser1);
  }

  @Test
  @DisplayName("회원가입 성공 테스트 Mock 사용X")
  void signup(){
    //given
    SignUpRequestDto requestDto = new SignUpRequestDto(
        "tester2",
        "1234",
        "testNickname2"
    );

    //when
    SignUpResponseDto testUser = userService.signUp(requestDto);

    //then
    assertNotNull(testUser);
  }

  @Test
  @DisplayName("회원가입 실패 테스트 - 아이디 중복 메서드 확인")
  void duplicateUsername(){
    //given
    SignUpRequestDto requestDto = new SignUpRequestDto(
        "tester",
        "1234",
        "testNickname2"
    );

    //when
    userService.duplicatedUsername(requestDto.getUsername());

    //then
    assertEquals(true, userRepository.existsByUsername(requestDto.getUsername()));
  }

  @Test
  @DisplayName("회원가입 실패 테스트 - 아이디 중복 메서드 확인")
  void duplicateNickname(){
    //given
    SignUpRequestDto requestDto = new SignUpRequestDto(
        "tester1",
        "1234",
        "testNickname"
    );

    //when
    userService.duplicatedNickName(requestDto.getNickname());

    //then
    assertEquals(true, userRepository.existsByNickname(requestDto.getNickname()));
  }



//  @Test
//  @DisplayName("회원가입 성공 테스트")
//  void signupTestSuccess() {
//    //given
//    SignUpRequestDto requestDto = new SignUpRequestDto(
//        "Tester1",
//        "1234",
//        "TesterNickname"
//    );
//    when(userRepository.save(any(User.class))).thenReturn(new User());
//
//    //when
//    SignUpResponseDto user1 = userService.signUp(requestDto);
//
//    //then
////    verify(userRepository, times(1)).save(any(User.class));
//    assertEquals(requestDto.getUsername(), user1.getUsername());
//  }
//
//  @Test
//  @DisplayName("회원가입 실패 테스트 - 중복된 사용자 이름")
//  void signupTestFail_DuplicatedUsername() {
//    // given
//    SignUpRequestDto requestDto = new SignUpRequestDto(
//        "Tester1",
//        "1234",
//        "TesterNickname");
//
////    when(userRepository.existsByUsername(requestDto.getUsername())).thenThrow(DuplicatedException.class);
//
//    // when & then
//    Exception exception = assertThrows(DuplicatedException.class, () -> {
//      userService.signUp(requestDto);
//    });
//
//    assertEquals(DUPLICATE_ID, exception.getMessage());
//    verify(userRepository, never()).save(any(User.class));
//  }
//
//  @Test
//  @DisplayName("회원가입 실패 테스트 - 중복된 닉네임")
//  void signupTestFail_DuplicatedNickname() {
//    // given
//    SignUpRequestDto requestDto = new SignUpRequestDto("Tester1", "1234", "TesterNickname");
//
//    when(userRepository.existsByUsername(requestDto.getUsername())).thenReturn(false);
//    when(userRepository.existsByNickname(requestDto.getNickname())).thenReturn(true);
//
//    // when & then
//    CustomException exception = assertThrows(CustomException.class, () -> {
//      userService.signUp(requestDto);
//    });
//
//    assertEquals(DUPLICATE_NICKNAME, exception.getMessage());
////    verify(userRepository, never()).save(any(User.class));
//  }
//
//
//  @Test
//  @DisplayName("회원 가입 성공 테스트")
//    // 테스트의 이름을 지정합니다.
//  void signupTest1() {
//    // given - 테스트를 위한 준비 단계
//    SignUpRequestDto requestDto = new SignUpRequestDto(
//        "Tester1",
//        "1234",
//        "TesterNickname");
//
//    // when - 실제로 테스트할 동작을 지정합니다.
//    // 이 경우, UserRepository의 existsByUserName 메서드를 호출하면 CustomException을 던지도록 설정합니다.
//    // when(userRepository.existsByUsername(anyString())).thenThrow(CustomException.class);
//
//    // then - 예상되는 결과를 검증합니다.
//    assertDoesNotThrow(
//        () -> userService.signUp(requestDto));  // signup 메서드가 예외를 던지지 않는지 확인합니다.
//    verify(userRepository, times(1)).save(any(User.class));
//  }
}
