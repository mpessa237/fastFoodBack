package com.herve.fastfood.services;

import com.herve.fastfood.dtos.FactureResponse;
import com.herve.fastfood.models.Commande;
import com.herve.fastfood.models.Facture;
import com.herve.fastfood.repositories.CommandeRepo;
import com.herve.fastfood.repositories.FactureRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FactureService {

    private final FactureRepo factureRepo;
    private final CommandeRepo commandeRepo;
    private final PdfService pdfService;

    @Transactional
    public FactureResponse generateFacture(Long commandeId) {
        Commande commande = commandeRepo.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID : " + commandeId));

        // Générer le PDF de la facture
        byte[] pdfBytes = pdfService.generatePdf(commande);

        // Créer et sauvegarder la facture
        Facture facture = new Facture();
        facture.setDateEmission(LocalDateTime.now());
        facture.setPdf(pdfBytes);
        facture.setCommande(commande);

        Facture savedFacture = factureRepo.save(facture);

        // Mettre à jour la commande avec la facture
        commande.setFacture(savedFacture);
        commandeRepo.save(commande);

        // Créer la réponse
        FactureResponse factureResponse = new FactureResponse();
        factureResponse.setFactureId(savedFacture.getFactureId());
        factureResponse.setCommandeId(commandeId);
        factureResponse.setDateEmission(savedFacture.getDateEmission());
        factureResponse.setPdfUrl("/api/factures/" + savedFacture.getFactureId() + "/pdf");

        return factureResponse;
    }
}
