package com.tekup.carpool_backend.repository.user;

import com.tekup.carpool_backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT U FROM User U WHERE U.email = :email")
    Optional<User> findByEmailWithRoles(String email);
}
