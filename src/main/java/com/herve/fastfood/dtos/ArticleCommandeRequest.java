package com.herve.fastfood.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCommandeRequest {
    @NotEmpty(message = "L'ID du menu est obligatoire")
    @NotBlank(message = "L'ID du menu est obligatoire")
    private Long menuId;

    @NotEmpty(message = "La quantité est obligatoire")
    @NotBlank(message = "La quantité est obligatoire")
    private Integer quantite;
}
