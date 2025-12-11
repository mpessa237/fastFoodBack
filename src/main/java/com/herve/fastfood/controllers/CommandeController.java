package com.herve.fastfood.controllers;

import com.herve.fastfood.dtos.CommandeRequest;
import com.herve.fastfood.dtos.CommandeResponse;
import com.herve.fastfood.models.Utilisateur;
import com.herve.fastfood.repositories.CommandeRepo;
import com.herve.fastfood.services.CommandeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/commandes")
public class CommandeController {

    private final CommandeService commandeService;

    @PostMapping
    public ResponseEntity<CommandeResponse> createCommande(
            @Valid @RequestBody CommandeRequest commandeRequest,
            @AuthenticationPrincipal Utilisateur utilisateur
            ){
        CommandeResponse commandeResponse = commandeService.createCommande(commandeRequest, utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(commandeResponse);
    }

    @PatchMapping("/{commandeId}/confirm")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CommandeResponse> confirmCommande(@PathVariable Long commandeId) {
        CommandeResponse commandeResponse = commandeService.confirmCommande(commandeId);
        return ResponseEntity.ok(commandeResponse);
    }

}
