package com.tekup.carpool_backend.config.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


@Service
public class JwtService {
    @Value("${carpool_app.security.jwt.secret}")
    private String jwtSecret;
    @Value("${carpool_app.security.jwt.expiration}")
    private long jwtExpirationMs; //1h

    public Claims extractAllClaims(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return (Claims) Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(keyBytes))
                .build()
                .parse(token)
                .getPayload();
    }
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }
    //Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))


    public String generateToken(Map<String, Objects> extraClaims, UserDetails userDetails){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        //.signWith(Keys.hmacShaKeyFor(keyBytes),SignatureAlgorithm.HS256)
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(keyBytes))
                .compact();
    }

    public boolean isTokenValid(String jwtToken,UserDetails userDetails){
        final String userName =  extractUsername(jwtToken);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }
}