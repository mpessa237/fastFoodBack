package com.herve.fastfood.dtos;

import com.herve.fastfood.models.Categorie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuRequest {
    private String nom;
    private String description;
    private Double prix;
    private Categorie categorie;

    private Boolean disponible = true;
}
