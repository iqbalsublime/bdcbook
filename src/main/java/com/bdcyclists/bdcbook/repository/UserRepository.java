package com.bdcyclists.bdcbook.repository;

import com.bdcyclists.bdcbook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByEmailAndPasswordResetHash(String email, String passwordResetHash);
}
