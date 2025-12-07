package com.herve.fastfood.dtos;

import com.herve.fastfood.models.StatusCommande;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandeResponse {
    private Long commandeId;
    private Long utilisateurId;
    private String nom; // Nom de l'utilisateur pour affichage
    private List<ArticleCommandeResponse> articles;
    private StatusCommande statusCommande;
    private LocalDateTime dateCommande;
    private Double totalCommande;
}
