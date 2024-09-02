package com.sparta.intern.service;

import com.sparta.intern.dto.SignUpRequestDto;
import com.sparta.intern.dto.SignUpResponseDto;
import com.sparta.intern.entity.User;
import com.sparta.intern.exception.DuplicatedException;
import com.sparta.intern.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.sparta.intern.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public SignUpResponseDto signUp(SignUpRequestDto requestDto){
    String username = requestDto.getUsername();
    String password = passwordEncoder.encode(requestDto.getPassword());
    String nickname = requestDto.getPassword();

    //username 중복확인
    if (duplicatedUsername(username)){
      throw new DuplicatedException(DUPLICATE_ID);
    }

    if (duplicatedNickName(nickname)){
      throw new DuplicatedException(DUPLICATE_NICKNAME);
    }

    User user = new User(
        username,
        password,
        nickname
    );
    userRepository.save(user);
    return new SignUpResponseDto(user);
  }

  public boolean duplicatedUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public boolean duplicatedNickName(String nickname){
    return userRepository.existsByNickname(nickname);
  }
}
