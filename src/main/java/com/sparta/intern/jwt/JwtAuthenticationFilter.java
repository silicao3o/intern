package com.sparta.intern.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.intern.dto.ForbiddenResponseDto;
import com.sparta.intern.dto.LoginRequestDto;
import com.sparta.intern.entity.UserAuthorities;
import com.sparta.intern.entity.UserStatusEnum;
import com.sparta.intern.exception.CommonErrorCode;
import com.sparta.intern.exception.ErrorType;
import com.sparta.intern.exception.NotFoundException;
import com.sparta.intern.exception.WithDrawException;
import com.sparta.intern.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtTokenHelper jwtTokenHelper;

  public JwtAuthenticationFilter(JwtTokenHelper jwtTokenHelper) {
    this.jwtTokenHelper = jwtTokenHelper;
    setFilterProcessesUrl("/users/login");
  }
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      LoginRequestDto requestDto = new ObjectMapper().readValue(
          request.getInputStream(), LoginRequestDto.class);

      Authentication authentication = getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(
              requestDto.getUsername(),
              requestDto.getPassword(),
              null
          )
      );

      // 사용자 상태 체크
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

      if (userDetails.getUser().getUserStatusEnum().equals(UserStatusEnum.WITHDRAW_USER)){
        throw new DisabledException("탈퇴한 사용자입니다.");
      }

      return authentication;

    } catch (IOException e) {
      log.error(e.getMessage());
      throw new NotFoundException(CommonErrorCode.TOKEN_ERROR);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException {
    String username = ((UserDetailsImpl)authResult.getPrincipal()).getUsername();
    UserAuthorities role = ((UserDetailsImpl)authResult.getPrincipal()).getUser().getAuthorities();

    log.debug("username: {}", username);
    log.debug("role: {}", role);

    String accessToken = jwtTokenHelper.createToken(username, role);
    String refreshToken = jwtTokenHelper.createRefreshToken();
    response.addHeader(JwtTokenHelper.AUTHORIZATION_HEADER, accessToken);
    response.addHeader(JwtTokenHelper.REFRESH_TOKEN_HEADER, refreshToken);
    response.addHeader(JwtTokenHelper.ACCESS_CONTROL_EXPOSE_HEADERS_HEADER, "Authorization, Refresh_token" );
    jwtTokenHelper.saveRefreshToken(username, refreshToken);


    //로그인 완료시 앞 7자리 제거 후 뒤에 토큰만 출력
    String token = accessToken.substring(7);

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(token);
    response.getWriter().flush();
  }


  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed) throws IOException {

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setCharacterEncoding("utf-8");

    if (failed instanceof DisabledException) {
      response.getWriter().write("상태 : " + response.getStatus() + ", " + failed.getMessage());
      //Body를 보내줘서
      if (failed instanceof WithDrawException){
        response.getWriter().write(new ObjectMapper()
            .writeValueAsString(new ForbiddenResponseDto(UserStatusEnum.WITHDRAW_USER, ErrorType.WITHDRAW_USER)));
      }
    } else {
      response.getWriter().write("상태 : " + response.getStatus() + ", 로그인 실패");
    }
  }
}
