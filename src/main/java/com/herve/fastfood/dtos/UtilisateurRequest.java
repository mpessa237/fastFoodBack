package com.herve.fastfood.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurRequest {
    @NotEmpty(message = "Nom is mandatory")
    @NotBlank(message = "Nom is mandatory")
    private String nom;
    @NotEmpty(message = "Adresse is mandatory")
    @NotBlank(message = "Adresse is mandatory")
    private String adresse;
    private String telephone;
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotEmpty(message = "MotDePasse is mandatory")
    @NotBlank(message = "MotDePasse is mandatory")
    private String motDePasse;
}
