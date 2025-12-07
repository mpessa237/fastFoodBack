package com.herve.fastfood.mappers;

import com.herve.fastfood.dtos.ArticleCommandeRequest;
import com.herve.fastfood.dtos.ArticleCommandeResponse;
import com.herve.fastfood.models.ArticleCommande;
import com.herve.fastfood.models.Menu;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleCommandeMapper {

    public ArticleCommande toEntity(ArticleCommandeRequest articleCommandeRequest, Menu menu){

        if (articleCommandeRequest == null || menu == null) {
            return null;
        }

        ArticleCommande articleCommande = new ArticleCommande();
        articleCommande.setMenu(menu);
        articleCommande.setQuantite(articleCommandeRequest.getQuantite() != null ? articleCommandeRequest.getQuantite() : 1);
        articleCommande.setPrixUnitaire(menu.getPrix());
        return articleCommande;
    }

    // Convertit entité ArticleCommande en ArticleCommandeResponse
    public ArticleCommandeResponse toDto(ArticleCommande articleCommande) {
        if (articleCommande == null) {
            return null;
        }

        ArticleCommandeResponse articleCommandeResponse = new ArticleCommandeResponse();
        articleCommandeResponse.setArticleCommandeId(articleCommande.getArticleCommandeId());
        articleCommandeResponse.setMenuId(articleCommande.getMenu().getMenuId());
        articleCommandeResponse.setNom(articleCommande.getMenu().getNom());
        articleCommandeResponse.setImage(articleCommande.getMenu().getImage());
        articleCommandeResponse.setQuantite(articleCommande.getQuantite());
        articleCommandeResponse.setPrixUnitaire(articleCommande.getPrixUnitaire());
        return articleCommandeResponse;
    }

    // Convertit une liste d'entités ArticleCommande en liste de ArticleCommandeResponse
    public List<ArticleCommandeResponse> toDtoList(List<ArticleCommande> articleCommandes) {
        if (articleCommandes == null) {
            return new ArrayList<>();
        }
        return articleCommandes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
