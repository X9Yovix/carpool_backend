package com.tekup.carpool_backend.repository.token;

import com.tekup.carpool_backend.model.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
            SELECT T
            FROM Token T
            INNER JOIN User U ON T.user.id = U.id
            WHERE U.id = :userId AND (T.expired = false OR T.revoked = false)
            """)
    List<Token> findAllValidTokensByUser(Long userId);

    Optional<Token> findByToken(String token);
}
