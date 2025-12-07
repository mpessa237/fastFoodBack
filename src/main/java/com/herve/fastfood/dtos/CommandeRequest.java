package com.herve.fastfood.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandeRequest {


    @NotEmpty(message = "L'ID de l'utilisateur est obligatoire")
    @NotBlank(message = "L'ID de l'utilisateur est obligatoire")
    private Long utilisateurId;

    @NotEmpty(message = "La commande doit contenir au moins un article")
    @NotBlank(message = "La commande doit contenir au moins un article")
    private List<ArticleCommandeRequest> articles;
}
