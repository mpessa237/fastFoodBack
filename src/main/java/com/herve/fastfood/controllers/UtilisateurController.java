package com.herve.fastfood.controllers;

import com.herve.fastfood.dtos.AdminRequest;
import com.herve.fastfood.dtos.LoginRequest;
import com.herve.fastfood.dtos.LoginResponse;
import com.herve.fastfood.dtos.UtilisateurRequest;
import com.herve.fastfood.models.Utilisateur;
import com.herve.fastfood.services.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class UtilisateurController {

    private final UtilisateurService utilisateurService ;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody UtilisateurRequest utilisateurRequest) {
        try {
            utilisateurService.register(utilisateurRequest);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Inscription réussie !"
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }


    @PostMapping("/admin/register")
    public ResponseEntity<Utilisateur> save(@Valid @RequestBody UtilisateurRequest utilisateurRequest){
        return ResponseEntity.ok(utilisateurService.registerAdmin(utilisateurRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("Requête d'authentification reçue pour: " + loginRequest.getEmail());

            LoginResponse response = this.utilisateurService.login(loginRequest);

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("message", "Email ou mot de passe incorrect")
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("message", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("message", "Une erreur est survenue: " + e.getMessage())
            );
        }
    }


}
