package com.herve.fastfood.controllers;

import com.herve.fastfood.dtos.AdminRequest;
import com.herve.fastfood.dtos.LoginRequest;
import com.herve.fastfood.dtos.LoginResponse;
import com.herve.fastfood.dtos.UtilisateurRequest;
import com.herve.fastfood.models.Utilisateur;
import com.herve.fastfood.services.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UtilisateurController {

    private final UtilisateurService utilisateurService ;

    @PostMapping("/register")
    public ResponseEntity<?> create(@Valid @RequestBody UtilisateurRequest utilisateurRequest ){

        utilisateurService.register(utilisateurRequest);
        return ResponseEntity.ok("inscription reussie");
    }

    @PostMapping("/admin/register")
    public ResponseEntity<Utilisateur> create(@Valid @RequestBody AdminRequest adminRequest){
        return ResponseEntity.ok(utilisateurService.registerAdmin(adminRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(this.utilisateurService.login(loginRequest));
    }

}
