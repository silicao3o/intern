package com.sparta.intern.controller;

import com.sparta.intern.dto.SignUpRequestDto;
import com.sparta.intern.dto.SignUpResponseDto;
import com.sparta.intern.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;


  @PostMapping("/signup")
  public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto){
    SignUpResponseDto responseDto = userService.signUp(requestDto);
    return ResponseEntity.ok().body(responseDto);
  }
}
