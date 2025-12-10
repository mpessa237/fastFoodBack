package com.herve.fastfood.services;

import com.herve.fastfood.dtos.AdminRequest;
import com.herve.fastfood.dtos.LoginRequest;
import com.herve.fastfood.dtos.LoginResponse;
import com.herve.fastfood.dtos.UtilisateurRequest;
import com.herve.fastfood.models.RefreshToken;
import com.herve.fastfood.models.Role;
import com.herve.fastfood.models.Utilisateur;
import com.herve.fastfood.repositories.RefreshTokenRepo;
import com.herve.fastfood.repositories.UtilisateurRepo;
import com.herve.fastfood.securities.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepo utilisateurRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepo refreshTokenRepo;
    private final RefreshTokenService refreshTokenService;


    public void register(UtilisateurRequest utilisateurRequest) {

        if (utilisateurRepo.existsByEmail(utilisateurRequest.getEmail())){
            throw new RuntimeException("email already exists!!");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(utilisateurRequest.getNom());
        utilisateur.setAdresse(utilisateurRequest.getAdresse());
        utilisateur.setTelephone(utilisateurRequest.getTelephone());
        utilisateur.setEmail(utilisateurRequest.getEmail());
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateurRequest.getMotDePasse()));
        utilisateur.setRole(Set.of(Role.CLIENT));

        utilisateurRepo.save(utilisateur);
    }


    public Utilisateur registerAdmin( AdminRequest adminRequest) {

        if (utilisateurRepo.existsByEmail(adminRequest.getEmail())){
            throw new RuntimeException("email already exists!!");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(adminRequest.getNom());
        utilisateur.setEmail(adminRequest.getEmail());
        utilisateur.setTelephone(adminRequest.getTelephone());
        utilisateur.setMotDePasse(passwordEncoder.encode(adminRequest.getMotDePasse()));
        utilisateur.setRole(Set.of(Role.ADMIN));

        return utilisateurRepo.save(utilisateur);
    }



    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getMotDePasse()
                )
        );

        var utilisateur = utilisateurRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("user not found!!"));


        var accessToken = jwtService.generateAccessToken(utilisateur);
        var refreshToken = jwtService.generateRefreshToken();

        refreshTokenRepo.save(new RefreshToken(utilisateur, refreshToken));

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setAccessTokenExpiration(JwtService.ACCESS_TOKEN_EXPIRATION);
        loginResponse.setRoles(
                utilisateur.getRole().stream()
                        .map(Role::name)
                        .collect(Collectors.toList())
        );
        return loginResponse;
    }

    public LoginResponse refreshToken(String refreshToken) {
        // 1. Vérification du Refresh Token
        var token = refreshTokenRepo.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh Token invalide"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepo.delete(token);
            throw new RuntimeException("Refresh Token expiré");
        }

        // 2. Génération d'un nouvel Access Token
        var utilisateur = token.getUtilisateur();
        var accessToken = jwtService.generateAccessToken(utilisateur);

        // 3. Construction de la réponse
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setAccessTokenExpiration(JwtService.ACCESS_TOKEN_EXPIRATION);
        loginResponse.setRoles(
                utilisateur.getRole().stream()
                        .map(Role::name)
                        .collect(Collectors.toList())
        );
        return loginResponse;
    }

    public void logout(String refreshToken) {
        refreshTokenRepo.findByToken(refreshToken)
                .ifPresent(refreshTokenRepo::delete);
    }
}
