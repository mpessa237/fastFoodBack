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
    private List<ArticleCommandeRequest> articles;
}
