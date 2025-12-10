package com.herve.fastfood.repositories;

import com.herve.fastfood.models.RefreshToken;
import com.herve.fastfood.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String refreshToken);

    void deleteByUtilisateur(Utilisateur utilisateur);

    boolean existsByUtilisateur(Utilisateur utilisateur);
}
