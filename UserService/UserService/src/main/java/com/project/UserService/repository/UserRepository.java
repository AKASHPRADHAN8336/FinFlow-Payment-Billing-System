package com.project.UserService.repository;

import com.project.UserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {



    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(Long phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(Long phone);
}
