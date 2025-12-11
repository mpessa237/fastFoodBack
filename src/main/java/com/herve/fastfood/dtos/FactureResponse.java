package com.herve.fastfood.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactureResponse {
    private Long factureId;
    private Long commandeId;
    private LocalDateTime dateEmission;
    private String pdfUrl; // URL pour télécharger le PDF de la facture
}
