package com.sparta.intern.security;

import com.sparta.intern.entity.User;
import com.sparta.intern.entity.UserAuthorities;
import com.sparta.intern.entity.UserStatusEnum;
import com.sparta.intern.exception.ErrorType;
import com.sparta.intern.exception.MismatchException;
import com.sparta.intern.exception.NotFoundException;
import com.sparta.intern.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NotFoundException(ErrorType.USER_NOT_FOUND));
    if (user.getUserStatusEnum() == UserStatusEnum.WITHDRAW_USER) {
      throw new MismatchException(ErrorType.WITHDRAW_USER);
    }

    return new UserDetailsImpl(user);
  }
}
