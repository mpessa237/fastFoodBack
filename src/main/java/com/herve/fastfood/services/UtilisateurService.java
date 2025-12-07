package com.herve.fastfood.services;

import com.herve.fastfood.dtos.AdminRequest;
import com.herve.fastfood.dtos.UtilisateurRequest;
import com.herve.fastfood.models.Role;
import com.herve.fastfood.models.Utilisateur;
import com.herve.fastfood.repositories.UtilisateurRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepo utilisateurRepo;
    private final PasswordEncoder passwordEncoder;


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
}
