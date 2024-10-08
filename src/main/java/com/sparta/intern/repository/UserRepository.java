package com.sparta.intern.repository;

import com.sparta.intern.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);

  boolean existsByNickname(String nickname);
}
