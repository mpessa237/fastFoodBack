package com.herve.fastfood.repositories;

import com.herve.fastfood.models.Utilisateur;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepo extends JpaRepository<Utilisateur,Long> {

    Optional<Utilisateur> findByEmail(String email);

    boolean existsByEmail(String email);
}
