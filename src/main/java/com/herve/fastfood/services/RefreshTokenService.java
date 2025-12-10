package com.herve.fastfood.services;

import com.herve.fastfood.models.RefreshToken;
import com.herve.fastfood.models.Utilisateur;
import com.herve.fastfood.repositories.RefreshTokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepo;

    @Transactional
    public RefreshToken createRefreshToken(String token,Utilisateur utilisateur) {
        RefreshToken refreshToken = new RefreshToken(utilisateur, token);
        return refreshTokenRepo.save(refreshToken);
    }

    // Trouve un RefreshToken par son token (UUID)
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    // Vérifie si un RefreshToken est valide (non expiré)
    public boolean isRefreshTokenValid(RefreshToken refreshToken) {
        if (refreshToken.isExpired()) {
            refreshTokenRepo.delete(refreshToken); // Supprime le token expiré
            return false;
        }
        return true;
    }

    // Supprime un RefreshToken (ex: lors d'une déconnexion)
    @Transactional
    public void deleteRefreshToken(String token) {
        refreshTokenRepo.findByToken(token).ifPresent(refreshTokenRepo::delete);
    }
}
