package com.tekup.carpool_backend.repository.password;

import com.tekup.carpool_backend.model.password.ResetPassword;
import com.tekup.carpool_backend.model.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword,Long> {
}
