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
    @NotEmpty(message = "Le nom est obligatoire")
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    @NotEmpty(message = "La description est obligatoire")
    @NotBlank(message = "La description est obligatoire")
    private String description;
    @NotEmpty(message = "La description est obligatoire")
    @NotBlank(message = "La description est obligatoire")
    private Double prix;
    @NotEmpty(message = "La description est obligatoire")
    @NotBlank(message = "La description est obligatoire")
    private Categorie categorie;

    private Boolean disponible = true;
}
