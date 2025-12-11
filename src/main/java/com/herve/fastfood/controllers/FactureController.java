package com.herve.fastfood.controllers;

import com.herve.fastfood.dtos.FactureResponse;
import com.herve.fastfood.models.Facture;
import com.herve.fastfood.repositories.FactureRepo;
import com.herve.fastfood.services.FactureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/factures")
public class FactureController {

    private final FactureService factureService;
    private final FactureRepo factureRepo;

    @PostMapping("/{commandeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<FactureResponse> generateFacture(@PathVariable Long commandeId) {
        FactureResponse factureResponse = factureService.generateFacture(commandeId);
        return ResponseEntity.ok(factureResponse);
    }

    @GetMapping("/{factureId}/pdf")
    public ResponseEntity<byte[]> getFacturePdf(@PathVariable Long factureId) {
        Facture facture = factureRepo.findById(factureId)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée avec l'ID : " + factureId));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("facture_" + factureId + ".pdf").build());

        return new ResponseEntity<>(facture.getPdf(), headers, HttpStatus.OK);
    }
}
