package com.herve.fastfood.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCommandeResponse {

    private Long articleCommandeId;
    private Long menuId;
    private String nom; // Nom du menu pour affichage
    private String image; // URL de l'image du menu
    private Integer quantite;
    private Double prixUnitaire;
}
