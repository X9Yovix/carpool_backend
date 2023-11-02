package com.tekup.carpool_backend.repository;

import com.tekup.carpool_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
