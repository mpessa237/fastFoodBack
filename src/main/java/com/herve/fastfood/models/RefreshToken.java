package com.herve.fastfood.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenId;
    private String token;
    private LocalDateTime expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    public RefreshToken(Utilisateur utilisateur, String token) {
        this.utilisateur = utilisateur;
        this.token = token;
        this.expiryDate = LocalDateTime.now().plusDays(7);
    }

    // Méthode isExpired()
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}
