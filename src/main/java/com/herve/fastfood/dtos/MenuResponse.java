package com.herve.fastfood.dtos;

import com.herve.fastfood.models.Categorie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponse {
    private Long menuId;
    private String nom;
    private String description;
    private String image;
    private Double prix;
    private Categorie categorie;

    private Boolean disponible;
}
