package com.herve.fastfood.securities;

import com.herve.fastfood.models.Role;
import com.herve.fastfood.models.Utilisateur;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    public static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15; // 15 minutes
    public static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;



    // Extrait le nom d'utilisateur d'un JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String generateAccessToken(UserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();
        if (userDetails instanceof Utilisateur) {
            List<String> roles = ((Utilisateur) userDetails).getRole()
                    .stream()
                    .map(Role::name)
                    .collect(Collectors.toList());
            claims.put("roles", roles);
        }
        return generateToken(claims, userDetails, ACCESS_TOKEN_EXPIRATION);
    }


    public String generateRefreshToken(){
        return UUID.randomUUID().toString();
    }

    private String generateToken(
            HashMap<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Valide un JWT
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            System.out.println("Token username: " + username);
            System.out.println("Token expiration: " + extractExpiration(token));
            System.out.println("Current time: " + new Date());
            boolean isValid = (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
            System.out.println("Is token valid? " + isValid);
            return isValid;
        } catch (ExpiredJwtException e) {
            System.out.println("Token is expired: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Token is invalid: " + e.getMessage());
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }

    // Extrait tous les claims d'un JWT
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expiré", e);
        } catch (JwtException e) {
            throw new RuntimeException("Token invalide", e);
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
