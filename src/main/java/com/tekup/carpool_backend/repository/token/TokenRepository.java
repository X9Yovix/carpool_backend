package com.tekup.carpool_backend.repository.token;

import com.tekup.carpool_backend.model.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
}
